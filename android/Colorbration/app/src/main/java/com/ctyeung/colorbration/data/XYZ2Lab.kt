package com.ctyeung.colorbration.data

class XYZ2Lab {

    var cieXYZ = Spectral2XYZ()
    var L: Double = 0.0
    var a: Double = 0.0
    var b: Double = 0.0

    fun XYZ2Lab() {

    }

    fun empty() = cieXYZ.empty()

    fun isEmpty() = cieXYZ.isEmpty()

    fun forward(): Boolean {
        // CIE 1976 formulation
        var Y: Double = (cieXYZ.Ys / cieXYZ.Yw)
        if (Y < .008856) {
            L = 903.3 * Y
            a = 500 * (cieXYZ.Xs / cieXYZ.Xw - Y)
            b = 200 * (Y - cieXYZ.Zs / cieXYZ.Zw)
        } else {
            L = 116 * Math.pow(Y, 1.0 / 3.0) - 16
            a = 500 * (Math.pow(cieXYZ.Xs / cieXYZ.Xw, 1.0 / 3.0) - Math.pow(Y, 1.0 / 3.0))
            b = 200 * (Math.pow(Y, 1.0 / 3.0) - Math.pow(cieXYZ.Zs / cieXYZ.Zw, 1.0 / 3.0))
        }

        return true
    }

    fun backward(): Boolean {
        var Y: Double = Math.pow(L / 116 + 16, 3.0)
        if (Y < .008856) {

        } else {
            Y = L / 903.3
        }
        return true
    }
}