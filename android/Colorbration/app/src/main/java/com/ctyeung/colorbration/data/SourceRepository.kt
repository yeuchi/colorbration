package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import com.ctyeung.colorbration.data.ref.LightSources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val prefStoreRepository: PrefStoreRepository
) {
    private val _event =
        MutableStateFlow<SourceDataEvent>(
            SourceDataEvent.Success(LightSources.ILLUMINANT_A),
        )
    val event: StateFlow<SourceDataEvent> = _event

    init {
        initSourceDataListener()
    }

    private fun initSourceDataListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                prefStoreRepository.getString(PrefStoreRepository.SOURCE_DATA).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = SourceDataEvent.Success(LightSources.ILLUMINANT_A)
                        }

                        else -> {
                            _event.value = SourceDataEvent.Success(it)
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("SourceRepository", it.toString())
        }
    }

    val illuminantA: SpectralReflectance
        get() {
            val reflectance = LightSources.let {
                SpectralReflectance(it.illuminantA)
            }
            return reflectance
        }

    val illuminantB: SpectralReflectance
        get() {
            val reflectance = LightSources.let {
                SpectralReflectance(it.illuminantB)
            }
            return reflectance
        }

    val illuminantC: SpectralReflectance
        get() {
            val reflectance = LightSources.let {
                SpectralReflectance(it.illuminantC)
            }
            return reflectance
        }

    val illuminantD50: SpectralReflectance
        get() {
            val reflectance = LightSources.let {
                SpectralReflectance(it.illuminantD50)
            }
            return reflectance
        }

    val illuminantD65: SpectralReflectance
        get() {
            val reflectance = LightSources.let {
                SpectralReflectance(it.illuminantD65)
            }
            return reflectance
        }

    suspend fun updateBy(selectedSource: String) {
        prefStoreRepository.setString(PrefStoreRepository.SOURCE_DATA, selectedSource)
    }
}

sealed class SourceDataEvent() {
    class Success(val selectedSource: String) : SourceDataEvent()
    class Error(val msg: String) : SourceDataEvent()
}