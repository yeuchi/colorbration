package com.ctyeung.colorbration.data

import android.graphics.PointF

/*
 * TODO Don't inherit, use cubic spline as composition to interpolate
 */
abstract class BaseSpectralData {
    var NAME: String = "Default 50% N";
    var name: String = NAME;
    var wavelength = arrayListOf(
    400, 410, 420, 430, 440,
    450, 460, 470, 480, 490,
    500, 510, 520, 530, 540,
    550, 560, 570, 580, 590,
    600, 610, 620, 630, 640,
    650, 660, 670, 680, 690,
    700
    )
}