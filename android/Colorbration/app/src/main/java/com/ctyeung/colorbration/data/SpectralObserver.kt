package com.ctyeung.colorbration.data

class SpectralObserver : BaseSpectralData {

    var tristimulus = ArrayList<Double>()

    val wavelength: ArrayList<Int>
        get() {
            return _wavelength
        }

    constructor(data: List<Double>) {

        data.let {
            for (i in 0 until wavelength.size) {
                // potential crash if data.size is too short
                tristimulus.add(data[i]);
            }
        }
    }

    /*
     * TODO add Cubic Spline for a smooth curve
     */
}