package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import com.ctyeung.colorbration.data.ref.LightSources
import com.ctyeung.colorbration.data.ref.StandardObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ColorDataRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val prefStoreRepository: PrefStoreRepository
) {
    private val _event =
        MutableStateFlow<ColorDataEvent>(
            ColorDataEvent.Sample(defaultSample),
        )
    val event: StateFlow<ColorDataEvent> = _event

    private var spectralReflectance: SpectralReflectance? = null

    init {
        initPrefStoreListener()
    }

    private fun initPrefStoreListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                prefStoreRepository.getString(PrefStoreRepository.ATTENUATOR_DATA).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ColorDataEvent.Sample(defaultSample)
                        }

                        else -> {
                            val listString = it.split(",")
                            val list = ArrayList<Double>()

                            for (item in listString) {
                                if (!item.isNullOrEmpty()) {
                                    list.add(item.toDouble())
                                }
                            }
                            spectralReflectance = SpectralReflectance(list)
                            spectralReflectance?.let {
                                _event.value = ColorDataEvent.Sample(it)
                            }
                        }
                    }
                }

                prefStoreRepository.getString(PrefStoreRepository.SOURCE_DATA).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ColorDataEvent.Source(LightSources.ILLUMINANT_A)
                        }

                        else -> _event.value = ColorDataEvent.Source(it)
                    }
                }

                prefStoreRepository.getString(PrefStoreRepository.OBSERVER_SELECTED).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ColorDataEvent.Source(StandardObserver.FUNC_2D_1931)
                        }

                        else -> _event.value = ColorDataEvent.Observer(it)
                    }
                }
            }
        }.onFailure {
            Log.e("ColorDataRepository", it.toString())
        }
    }

    val defaultSample: SpectralReflectance
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

    suspend fun updateBy(index: Int, value: Double) {
        spectralReflectance?.let {
            it.updateBy(index, value)
            prefStoreRepository.setString(
                PrefStoreRepository.ATTENUATOR_DATA,
                spectralReflectance.toString()
            )
        }
    }
}

sealed class ColorDataEvent() {
    class Sample(val curve: SpectralReflectance) : ColorDataEvent()
    class Source(val illuminantType: String) : ColorDataEvent()
    class Observer(val observerType: String) : ColorDataEvent()
    class Error(val msg: String) : ColorDataEvent()
}