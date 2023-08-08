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

    val knots:List<Pair<Int, Double>>
        get() {
            map?.apply {
                return this.toList().sortedBy { it.first }
            }
            return emptyList()
        }

    private var map: HashMap<Int, Double>? = null

    constructor(data: List<Double>) {

        data.let {
            map = HashMap<Int, Double>()
            map?.let { m ->
                for (i in 0 until _wavelength.size) {
                    m.set(_wavelength[i], data[i])
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