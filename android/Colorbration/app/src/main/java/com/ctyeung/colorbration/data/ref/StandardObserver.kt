package com.ctyeung.colorbration.data.ref

import com.ctyeung.colorbration.data.BaseSpectralData

object StandardObserver : BaseSpectralData() {

    const val FUNC_2D_1931: String = "2 degree 1931"
    const val FUNC_10D_1964: String = "10 degree 1964"

    val count = 31

    val wavelength:ArrayList<Int>
        get() {
            return _wavelength
        }

    val standardObserver10Degree1964X = listOf<Double>(
        .0191, .0847, .2045, .3147, .3837,
        .3707, .3023, .1956, .0805, .0162,
        .0038, .0375, .1177, .2365, .3768,
        .5298, .7052, .8787, 1.0142, 1.1185,
        1.1240, 1.0305, .8563, .6475, .4316,
        .2683, .1526, .0813, .0409, .0199, .0096
    )

    val standardObserver10Degree1964Y = listOf<Double>(
        .002, .0088, .0214, .0387, .0621,
        .0895, .1282, .1852, .2536, .3391,
        .4608, .6067, .7618, .8752, .9620,
        .9918, .9973, .9556, .8689, .7774,
        .6583, .5280, .3981, .2835, .1798,
        .1076, .0603, .0318, .0159, .0077, .0037
    )

    val standardObserver10Degree1964Z = listOf<Double>(
        .086, .3894, .9725, 1.5535, 1.9673,
        1.9948, 1.7454, 1.3176, .7721, .4153,
        .2185, .1120, .0607, .0305, .0137,
        .004, .00, .00, .00, .00,
        .00, .00, .00, .00, .00,
        .00, .00, .00, .00, .00, .00
    )

    val standardObserver2Degree1931X = listOf<Double>(
        .0143, .0435, .1344, .2839, .3483,
        .3362, .2908, .1954, .0956, .0320,
        .0049, .0093, .0633, .1655, .2904,
        .4334, .5945, .7621, .9163, 1.0263,
        1.0622, 1.0026, .8544, .6424, .4479,
        .2835, .1649, .0874, .0468, .0227,
        .0114
    )

    val standardObserver2Degree1931Y = listOf<Double>(
        .0004, .0012, .0040, .0116, .0230,
        .0380, .0600, .0910, .1390, .2080,
        .3230, .5030, .7100, .8620, .9540,
        .9950, .9950, .9520, .8700, .7570,
        .6310, .5030, .3810, .2650, .1750,
        .1070, .0610, .0320, .0170, .0082,
        .0041
    )

    val standardObserver2Degree1931Z = listOf<Double>(
        .0679, .2074, .6456, 1.3856, 1.7471,
        1.7721, 1.6692, 1.2876, .8130, .4652,
        .2720, .1582, .0782, .0422, .0203,
        .0087, .0039, .0021, .0017, .0011,
        .0008, .0003, .0002, .0000, .0000,
        .0000, .0000, .0000, .0000, .0000,
        .0000
    )
}