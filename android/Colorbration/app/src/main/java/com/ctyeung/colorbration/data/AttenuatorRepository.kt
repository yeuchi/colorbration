package com.ctyeung.colorbration.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.ctyeung.colorbration.data.math.Spectral2XYZ
import com.ctyeung.colorbration.data.math.XYZ2sRGB
import com.ctyeung.colorbration.viewmodels.ObserverEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttenuatorRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val colorDataRepository: ColorDataRepository
) {
    private val _event =
        MutableStateFlow<AttenuatorEvent>(
            AttenuatorEvent.Success(Color.LightGray, colorDataRepository.defaultSample),
        )
    val event: StateFlow<AttenuatorEvent> = _event

    init {
        initDataChangeListener()
    }

    private fun initDataChangeListener() {
        kotlin.runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                colorDataRepository.event.collect() {
                    when (it) {
                        is ColorDataEvent.Sample -> {
                            _event.value = AttenuatorEvent.Success(Color.LightGray, it.curve)
                        }

                        else -> {
                            /*
                             * TODO handle other changes and update sRGB color
                             */
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("AttenuatorRepository", it.toString())
        }
    }

    fun findsRGB(source:SpectralReflectance,
    sample:SpectralReflectance,
    observerType:String): android.graphics.Color? {
        val sourceXYZ = Spectral2XYZ.findIlluminantXYZ(source, observerType)
        val sampleXYZ = Spectral2XYZ.findAttenuatorXYZ(source, sample, observerType, sourceXYZ.Y)
        val color = XYZ2sRGB().forward(sampleXYZ.X, sampleXYZ.Y, sampleXYZ.Z)
        return color
    }

    suspend fun updateBy(index: Int, value: Double) = colorDataRepository.updateBy(index, value)
}

sealed class AttenuatorEvent() {
    class Success(val sRGB:Color, val curve: SpectralReflectance) : AttenuatorEvent()
    class Error(val msg: String) : AttenuatorEvent()
}