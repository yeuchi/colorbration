package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.data.ref.LightSources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SourceRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    val IlluminantA: List<SpectralData>
        get() {
            val list = LightSources.let {
                listOf(
                    SpectralData(it.illuminantA),
                )
            }
            return list
        }

    val IlluminantB: List<SpectralData>
        get() {
            val list = LightSources.let {
                listOf(
                    SpectralData(it.illuminantB),
                )
            }
            return list
        }

    val IlluminantC: List<SpectralData>
        get() {
            val list = LightSources.let {
                listOf(
                    SpectralData(it.illuminantC),
                )
            }
            return list
        }

    val IlluminantD50: List<SpectralData>
        get() {
            val list = LightSources.let {
                listOf(
                    SpectralData(it.illuminantD50),
                )
            }
            return list
        }


    val IlluminantD65: List<SpectralData>
        get() {
            val list = LightSources.let {
                listOf(
                    SpectralData(it.illuminantD65),
                )
            }
            return list
        }
}