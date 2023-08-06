package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.data.ref.LightSources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SourceRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
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
}