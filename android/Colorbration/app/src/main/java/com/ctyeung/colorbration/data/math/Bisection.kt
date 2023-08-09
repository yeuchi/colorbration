package com.ctyeung.colorbration.data.math

open class Bisection {

    var arraySrcX = ArrayList<Double>()

    /*
     * bisection search to locate x-axis values for input
     * - intended as a private method
     */
    fun findNearest(ab: Double): Int {                                               // x-axis value
        var ju = arraySrcX.size                                                // upper limit
        var jl = 0                                                                // lower limit
        var jm: Int?                                                            // midpoint

        while (ju - jl > 1) {
            jm = (ju + jl) / 2                                    // midpoint formula

            if (ab > arraySrcX[jm])
                jl = jm
            else
                ju = jm
        }
        return jl
    }
}