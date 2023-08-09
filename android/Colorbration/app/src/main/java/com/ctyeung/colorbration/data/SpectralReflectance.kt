package com.ctyeung.colorbration.data

import com.ctyeung.colorbration.data.math.MyPoint

class SpectralReflectance : BaseSpectralData {
    private var _percent = ArrayList<Double>()
    val percent: List<Double>
        get() {
            return _percent
        }

    constructor(data: List<Double>) {

        data.let {
            for(item in data) {
                _percent.add(item)
            }
        }
    }

    /*
     * TODO add Cubic Spline for a smooth curve
     */

    fun updateBy(index:Int, value:Double) {
        _percent[index] = value
    }
}