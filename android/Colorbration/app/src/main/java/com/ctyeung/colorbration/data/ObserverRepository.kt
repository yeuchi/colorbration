package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import com.ctyeung.colorbration.data.ref.StandardObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ObserverRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val prefStoreRepository: PrefStoreRepository
) {
    private val _event =
        MutableStateFlow<ObserverDataEvent>(
            ObserverDataEvent.Success(StandardObserver.FUNC_2D_1931),
        )
    val event: StateFlow<ObserverDataEvent> = _event

    init {
        initDataStoreListener()
    }

    private fun initDataStoreListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                prefStoreRepository.getString(PrefStoreRepository.OBSERVER_SELECTED).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ObserverDataEvent.Success(StandardObserver.FUNC_2D_1931)
                        }

                        else -> {
                            _event.value = ObserverDataEvent.Success(it)
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("ObserverRepository", it.toString())
        }
    }

    val standardObserver10Degree: List<SpectralObserver>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralObserver(it.standardObserver10Degree1964X),
                    SpectralObserver(it.standardObserver10Degree1964Y),
                    SpectralObserver(it.standardObserver10Degree1964Z)
                )
            }
            return list
        }

    val standardObserver2Degree: List<SpectralObserver>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralObserver(it.standardObserver2Degree1931X),
                    SpectralObserver(it.standardObserver2Degree1931Y),
                    SpectralObserver(it.standardObserver2Degree1931Z)
                )
            }
            return list
        }

    suspend fun updateBy(selectedObserver: String) {
        prefStoreRepository.setString(PrefStoreRepository.OBSERVER_SELECTED, selectedObserver)
    }
}

sealed class ObserverDataEvent() {
    class Success(val selectedObserver:String) : ObserverDataEvent()
    class Error(val msg: String) : ObserverDataEvent()
}