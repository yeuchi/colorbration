package com.ctyeung.colorbration.data

import com.ctyeung.colorbration.data.math.MyPoint

class SpectralReflectance : BaseSpectralData {

    var percent = ArrayList<Double>()

    constructor(data: List<Double>) {

        data.let {
            for (i in 0 until wavelength.size) {
                // potential crash if data.size is too short
                percent.add(data[i]);
            }
        }
    }

    /*
     * TODO add Cubic Spline for a smooth curve
     */

    fun add(point: MyPoint) {

    }

    fun clear() {

    }
}