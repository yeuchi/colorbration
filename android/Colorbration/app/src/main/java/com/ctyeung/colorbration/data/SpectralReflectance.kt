package com.ctyeung.colorbration.data

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

    override fun toString():String {
        val strBuilder = StringBuilder()
        for (item in _percent) {
            strBuilder.append("${item},")
        }
        return strBuilder.toString().dropLast(1)
    }
}