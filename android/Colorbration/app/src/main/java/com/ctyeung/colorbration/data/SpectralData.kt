package com.ctyeung.colorbration.data

import android.graphics.PointF

open class SpectralData : Interpolate {
    var NAME: String = "Default 50% N";
    var name: String = NAME;
    var wavelength = ArrayList<Int>()
    var percent = ArrayList<Double>()

    constructor(data: List<Double>? = null) {
        onInitWavelengthData()

        data?.let {
            for (i in 0 until wavelength.size) {
                // potential crash if data.size is too short
                percent.add(data[i]);
            }
        } ?: run {
            // default gray
            onInitPercentData();
        }
    }

    fun onInitWavelengthData() {
        wavelength = arrayListOf(
            400, 410, 420, 430, 440,
            450, 460, 470, 480, 490,
            500, 510, 520, 530, 540,
            550, 560, 570, 580, 590,
            600, 610, 620, 630, 640,
            650, 660, 670, 680, 690,
            700
        );
    }

    fun onInitPercentData() {
        percent.clear()

        for (i in 0 until wavelength.size) {
            percent.add(50.0);
        }
    }

    fun onInitCurve(): ArrayList<PointF> {
        var data = ArrayList<PointF>()
        for (i in 0 until wavelength.size) {
            val pt = PointF(wavelength[i].toFloat(), percent[i].toFloat());
            data.add(pt);
        }
        return data;
    }
}