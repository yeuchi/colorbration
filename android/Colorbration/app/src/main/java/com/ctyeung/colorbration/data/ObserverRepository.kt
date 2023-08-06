package com.ctyeung.colorbration.data

import android.content.Context
import com.ctyeung.colorbration.SpectralObserver
import com.ctyeung.colorbration.data.ref.StandardObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ObserverRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    val standardObserver10Degree: List<SpectralObserver>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralObserver(it.standardObserver10Degree1964X),
                    SpectralObserver(it.standardObserver10Degree1964Y),
                    SpectralObserver(it.standardObserver10Degree1964Z)
                )
            }
            return list
        }

    val standardObserver2Degree: List<SpectralObserver>
        get() {
            val list = StandardObserver.let {
                listOf(
                    SpectralObserver(it.standardObserver2Degree1931X),
                    SpectralObserver(it.standardObserver2Degree1931Y),
                    SpectralObserver(it.standardObserver2Degree1931Z)
                )
            }
            return list
        }
}