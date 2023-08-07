package com.ctyeung.colorbration.data

import com.ctyeung.colorbration.data.math.MyPoint

class SpectralReflectance : BaseSpectralData {
    val percent: List<Double>
        get() {
            map?.apply {
                return values.toList()
            }
            return emptyList<Double>()
        }

    private var map: HashMap<Int, Double>? = null

    constructor(data: List<Double>) {

        data.let {
            map = HashMap<Int, Double>()
            map?.let { m ->
                for (i in 0 until wavelength.size) {
                    m.set(i, data[i])
                }
            }
        }
    }

    /*
     * TODO add Cubic Spline for a smooth curve
     */

    fun add(point: MyPoint) {
        map?.apply {
            this[point.x.toInt()] = point.y
        }
    }

    fun clear() = map?.clear()
}