package com.ctyeung.colorbration.data

import android.graphics.Point

class Spectral2XYZ {
    var observer = StandardObserver()
    var _sample: SpectralData? = null
    var _source: SpectralData? = null

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
    var sample: SpectralData?
        get() = _sample
        set(value) {
            _sample = value
            _sample?.let { sam ->
                _source?.let { src ->

                    Xs = 0.0
                    Ys = 0.0
                    Zs = 0.0
                    for (i in 0 until observer.count) {
                        /*
                         * TODO add option to choose 10degree
                         */
                        val wavelength = observer.wavelength[i].toDouble()
                        var observerX = observer.standardObserver2Degree1931X[i];
                        var observerY = observer.standardObserver2Degree1931Y[i];
                        var observerZ = observer.standardObserver2Degree1931Z[i];
                        Xs += observerX * sam.getY(wavelength) / 100.0 * src.getY(
                            wavelength
                        ) / 100.0
                        Ys += observerY * sam.getY(wavelength) / 100.0 * src.getY(
                            wavelength
                        ) / 100.0
                        Zs += observerZ * sam.getY(wavelength) / 100.0 * src.getY(
                            wavelength
                        ) / 100.0
                    }
                    Kw = 100.0 / Yw

                    Xs *= Kw
                    Ys *= Kw
                    Zs *= Kw
                }
            }
        }

    var source: SpectralData?
        get() = _source
        set(value) {
            _source = value
            _sample?.let { sam ->
                _source?.let { src ->

                    Xs = 0.0
                    Ys = 0.0
                    Zs = 0.0
                    for (i in 0 until observer.count) {
                        val wavelength = observer.wavelength[i].toDouble()
                        /*
                         * TODO add option to choose 10degree
                         */
                        var observerX = observer.standardObserver2Degree1931X[i];
                        var observerY = observer.standardObserver2Degree1931Y[i];
                        var observerZ = observer.standardObserver2Degree1931Z[i];
                        Xs += observerX * src.getY(
                            wavelength
                        ) / 100.0
                        Ys += observerY * src.getY(
                            wavelength
                        ) / 100.0
                        Zs += observerZ * src.getY(
                            wavelength
                        ) / 100.0
                    }
                    Xs *= Kw
                    Ys *= Kw
                    Zs *= Kw
                }
            }
        }
}