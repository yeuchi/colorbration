package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import com.ctyeung.colorbration.data.ref.Illuminants
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

    private var _sample: SpectralAttenuator? = null
    private var _sourceType: String = Illuminants.ILLUMINANT_A
    private var _observerType: String = StandardObserver.FUNC_2D_1931

    val sample: SpectralAttenuator?
        get() {
            return _sample
        }
    val sourceType: String
        get() {
            return _sourceType
        }
    val source: SpectralAttenuator
        get() {
            val list = Illuminants.retrieve(_sourceType)
            return SpectralAttenuator(list)
        }
    val observerType: String
        get() {
            return _observerType
        }

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
                            _sample = SpectralAttenuator(list)
                            _sample?.let {
                                _event.value = ColorDataEvent.Sample(it)
                            }
                        }
                    }
                }

                prefStoreRepository.getString(PrefStoreRepository.SOURCE_DATA).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ColorDataEvent.Source(Illuminants.ILLUMINANT_A)
                        }

                        else -> {
                            _sourceType = it
                            _event.value = ColorDataEvent.Source(it)
                        }
                    }
                }

                prefStoreRepository.getString(PrefStoreRepository.OBSERVER_SELECTED).collect() {
                    when {
                        it.isNullOrBlank() || it.isEmpty() -> {
                            _event.value = ColorDataEvent.Source(StandardObserver.FUNC_2D_1931)
                        }

                        else -> {
                            _observerType = it
                            _event.value = ColorDataEvent.Observer(it)
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("ColorDataRepository", it.toString())
        }
    }

    val defaultSample: SpectralAttenuator
        get() {
            val list = ArrayList<Double>()
            for (i in 0..30) {
                list.add(50.0)
            }
            SpectralAttenuator(list).apply {
                _sample = this
                return this
            }
        }

    suspend fun updateBy(index: Int, value: Double) {
        _sample?.let {
            it.updateBy(index, value)
            prefStoreRepository.setString(
                PrefStoreRepository.ATTENUATOR_DATA,
                _sample.toString()
            )
        }
    }
}

sealed class ColorDataEvent() {
    class Sample(val curve: SpectralAttenuator) : ColorDataEvent()
    class Source(val illuminantType: String) : ColorDataEvent()
    class Observer(val observerType: String) : ColorDataEvent()
    class Error(val msg: String) : ColorDataEvent()
}