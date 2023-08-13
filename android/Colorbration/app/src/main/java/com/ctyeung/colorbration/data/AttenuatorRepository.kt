package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttenuatorRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val prefStoreRepository: PrefStoreRepository
) {
    private val _event =
        MutableStateFlow<AttenuatorEvent>(
            AttenuatorEvent.Success(defaultSample),
        )
    val event: StateFlow<AttenuatorEvent> = _event

    private var spectralReflectance: SpectralReflectance? = null

     private val defaultSample : SpectralReflectance
        get() {
            val list = ArrayList<Double>()
            for (i in 0..30) {
                list.add(.50)
            }
            SpectralReflectance(list).apply {
                spectralReflectance = this
                return this
            }
        }

    init {
        initDataStoreListener()
    }

    private fun initDataStoreListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                prefStoreRepository.getString(PrefStoreRepository.ATTENUATOR_DATA).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = AttenuatorEvent.Success(defaultSample)
                        }

                        else -> {
                            val listString = it.split(",")
                            val list = ArrayList<Double>()

                            for(item in listString) {
                                if(!item.isNullOrEmpty()) {
                                    list.add(item.toDouble())
                                }
                            }
                            spectralReflectance = SpectralReflectance(list)
                            spectralReflectance?.let{
                                _event.value = AttenuatorEvent.Success(it)
                            }
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("AttenuatorRepository", it.toString())
        }
    }

    suspend fun updateBy(index: Int, value: Double) {
        spectralReflectance?.let {
            it.updateBy(index, value)
            prefStoreRepository.setString(PrefStoreRepository.ATTENUATOR_DATA, spectralReflectance.toString())
        }
    }
}

sealed class AttenuatorEvent() {
    class Success(val curve: SpectralReflectance) : AttenuatorEvent()
    class Error(val msg: String) : AttenuatorEvent()
}