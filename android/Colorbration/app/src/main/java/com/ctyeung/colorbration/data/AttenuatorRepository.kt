package com.ctyeung.colorbration.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AttenuatorRepository  @Inject constructor(
    @ApplicationContext val context: Context
) {
    val Default: SpectralReflectance
        get() {
            val list = ArrayList<Double>()
            for(i in 0..30) {
                list.add(50.0)
            }
            return SpectralReflectance(list)
        }
}