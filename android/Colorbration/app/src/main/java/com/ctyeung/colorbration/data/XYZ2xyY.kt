package com.ctyeung.colorbration.data

class XYZ2xyY {
    // Chromaticity xy
    var xc: Double = 0.0
    var yc: Double = 0.0

    // Tristimulus XYZ
    var X: Double = 0.0
    var Y: Double = 0.0
    var Z: Double = 0.0

    fun empty() {
        xc = 0.0
        yc = 0.0
        X = 0.0
        Y = 0.0
        Z = 0.0
    }

    fun isEmpty(): Boolean {
        if (xc == 0.0 && yc == 0.0 && X == 0.0 && Y == 0.0 && Z == 0.0)
            return true
        return false
    }

    fun forward(): Boolean {
        val denom: Double = (X + Y + Z);
        xc = X / denom
        yc = Y / denom
        return true
    }

    fun backward(): Boolean {
        return true
    }
}