package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.data.ref.StandardObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ObserverRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    val standardObserver10Degree: List<SpectralData>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralData(it.standardObserver10Degree1964X),
                    SpectralData(it.standardObserver10Degree1964Y),
                    SpectralData(it.standardObserver10Degree1964Z)
                )
            }
            return list
        }

    val standardObserver2Degree: List<SpectralData>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralData(it.standardObserver2Degree1931X),
                    SpectralData(it.standardObserver2Degree1931Y),
                    SpectralData(it.standardObserver2Degree1931Z)
                )
            }
            return list
        }
}