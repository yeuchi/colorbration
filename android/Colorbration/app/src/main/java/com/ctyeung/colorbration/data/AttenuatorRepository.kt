package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.data.math.MyPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AttenuatorRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
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

    fun add(p:MyPoint) {
        spectralReflectance?.let {
            it.add(p)
        }
    }
}