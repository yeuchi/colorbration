package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.data.math.MyPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AttenuatorRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val _event =
        MutableStateFlow<AttenuatorEvent>(
            AttenuatorEvent.Success(sample),
        )
    val event: StateFlow<AttenuatorEvent> = _event


    private var spectralReflectance: SpectralReflectance? = null

    val sample: SpectralReflectance
        get() {
            spectralReflectance?.let {
                return it
            }

            val list = ArrayList<Double>()
            for (i in 0..30) {
                list.add(.50)
            }
            SpectralReflectance(list).apply {
                spectralReflectance = this
                return this
            }
        }

    fun updateBy(index: Int, value: Double) {
        spectralReflectance?.let {
            it.updateBy(index, value)
        }
    }
}

sealed class AttenuatorEvent() {
    object InProgress : AttenuatorEvent()
    class Success(val curve: SpectralReflectance) : AttenuatorEvent()
    class Error(val msg: String) : AttenuatorEvent()
}