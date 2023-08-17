package com.ctyeung.colorbration.data.math

data class CIELab(val L:Double, val a:Double, val b:Double)
class XYZ2Lab {

    fun forward(source:Tristimulus, sample:Tristimulus): CIELab {
        var L: Double = 0.0
        var a: Double = 0.0
        var b: Double = 0.0

        // CIE 1976 formulation
        var Y: Double = (sample.Y / source.Y)
        if (Y < .008856) {
            L = 903.3 * Y
            a = 500 * (sample.X / source.X - Y)
            b = 200 * (Y - sample.Z / source.Z)
        } else {
            L = 116 * Math.pow(Y, 1.0 / 3.0) - 16
            a = 500 * (Math.pow(sample.X / source.X, 1.0 / 3.0) - Math.pow(Y, 1.0 / 3.0))
            b = 200 * (Math.pow(Y, 1.0 / 3.0) - Math.pow(sample.Z / source.Z, 1.0 / 3.0))
        }
        return CIELab(L, a, b)
    }

    fun backward(L: Double): Double {
        var Y: Double = Math.pow(L / 116 + 16, 3.0)
        if (Y < .008856) {

        } else {
            Y = L / 903.3
        }
        return Y
    }
}