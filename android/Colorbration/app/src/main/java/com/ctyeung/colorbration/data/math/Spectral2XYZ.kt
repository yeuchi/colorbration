package com.ctyeung.colorbration.data.math

import com.ctyeung.colorbration.data.SpectralReflectance
import com.ctyeung.colorbration.data.ref.StandardObserver

class Spectral2XYZ {
    var _sample: SpectralReflectance? = null
    var _source: SpectralReflectance? = null

    var Kw: Double = 0.0                                // factor to bring Y to 100%
    var Xw: Double = 0.0
    var Yw: Double = 0.0
    var Zw: Double = 0.0

    var Xs: Double = 0.0
    var Ys: Double = 0.0
    var Zs: Double = 0.0


    fun empty() {
        _sample = null
        _source = null
    }

    fun isEmpty(): Boolean {
        if (_sample != null || _source != null)
            return false
        return true
    }

    // ----------------------------------------------------------
    // Produce Tristimulus values:
    // take the integral of sample and standard observer curves.
    // ----------------------------------------------------------
    var sample: SpectralReflectance?
        get() = _sample
        set(value) {
            _sample = value
            _sample?.let { sam ->
                _source?.let { src ->

                    Xs = 0.0
                    Ys = 0.0
                    Zs = 0.0
                    StandardObserver.let {
                        for (i in 0 until StandardObserver.count) {
                            /*
                         * TODO add option to choose 10degree
                         */
                            val wavelength = it.wavelength[i].toDouble()
                            var observerX = StandardObserver.standardObserver2Degree1931X[i];
                            var observerY = StandardObserver.standardObserver2Degree1931Y[i];
                            var observerZ = StandardObserver.standardObserver2Degree1931Z[i];
                            Xs += observerX * sam.percent[i] / 100.0 * src.percent[i] / 100.0
                            Ys += observerY * sam.percent[i]/ 100.0 * src.percent[i] / 100.0
                            Zs += observerZ * sam.percent[i] / 100.0 * src.percent[i] / 100.0
                        }
                    }
                    Kw = 100.0 / Yw

                    Xs *= Kw
                    Ys *= Kw
                    Zs *= Kw
                }
            }
        }

    var source: SpectralReflectance?
        get() = _source
        set(value) {
            _source = value
            _sample?.let { sam ->
                _source?.let { src ->

                    Xs = 0.0
                    Ys = 0.0
                    Zs = 0.0
                    StandardObserver.let {
                        for (i in 0 until StandardObserver.count) {
                            /*
                         * TODO add option to choose 10degree (pref storage)
                         *  Can't remember how the observer values are scaled (2.0 range?)
                         */
                            var observerX = StandardObserver.standardObserver2Degree1931X[i];
                            var observerY = StandardObserver.standardObserver2Degree1931Y[i];
                            var observerZ = StandardObserver.standardObserver2Degree1931Z[i];
                            Xs += observerX * src.percent[i] / 100.0
                            Ys += observerY * src.percent[i] / 100.0
                            Zs += observerZ * src.percent[i] / 100.0
                        }
                    }
                    Xs *= Kw
                    Ys *= Kw
                    Zs *= Kw
                }
            }
        }
}