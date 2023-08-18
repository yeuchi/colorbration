package com.ctyeung.colorbration.data.math

import com.ctyeung.colorbration.data.SpectralAttenuator
import com.ctyeung.colorbration.data.ref.StandardObserver

data class Tristimulus(val X:Double, val Y:Double, val Z:Double)
object Spectral2XYZ {

    // ----------------------------------------------------------
    // Produce Tristimulus values:
    // take the integral of sample and standard observer curves.
    // ----------------------------------------------------------
    fun findAttenuatorXYZ(
        source: SpectralAttenuator,
        sample: SpectralAttenuator,
        observerType: String,
        Yw: Double,
    ): Tristimulus {
        var Xs = 0.0
        var Ys = 0.0
        var Zs = 0.0
        val observer = retrieveObserverCurves(observerType)
        for (i in 0 until StandardObserver.count) {
            Xs += observer[0][i] * sample.percent[i] / 100.0 * source.percent[i] / 100.0
            Ys += observer[1][i] * sample.percent[i] / 100.0 * source.percent[i] / 100.0
            Zs += observer[2][i] * sample.percent[i] / 100.0 * source.percent[i] / 100.0
        }
        var Kw = 100.0 / Yw

        Xs *= Kw
        Ys *= Kw
        Zs *= Kw

        return Tristimulus(Xs, Ys, Zs)
    }

    fun findIlluminantXYZ(
        source: SpectralAttenuator,
        observerType: String
    ): Tristimulus {
        var Xw = 0.0
        var Yw = 0.0
        var Zw = 0.0
        val observer = retrieveObserverCurves(observerType)
        for (i in 0 until StandardObserver.count) {
            Xw += observer[0][i] * source.percent[i] / 100.0
            Yw += observer[1][i] * source.percent[i] / 100.0
            Zw += observer[2][i] * source.percent[i] / 100.0
        }
        var Kw = 100.0 / Yw;
        Xw *= Kw
        Yw *= Kw
        Zw *= Kw

        return Tristimulus(Xw, Yw, Zw)
    }

    private fun retrieveObserverCurves(type: String): List<List<Double>> {
        return when (type) {
            StandardObserver.FUNC_2D_1931 -> listOf(
                StandardObserver.standardObserver2Degree1931X,
                StandardObserver.standardObserver2Degree1931Y,
                StandardObserver.standardObserver2Degree1931Z
            )

//           StandardObserver.FUNC_10D_1964,
            else -> listOf(
                StandardObserver.standardObserver10Degree1964X,
                StandardObserver.standardObserver10Degree1964X,
                StandardObserver.standardObserver10Degree1964X
            )
        }
    }
}