package com.ctyeung.colorbration.data.math

import android.graphics.Color

// Reference:	http://en.wikipedia.org/wiki/SRGB#The_forward_transformation_.28CIE_xyY_or_CIE_XYZ_to_sRGB.29
class XYZ2sRGB : Matrix3x3() {

    init {
        // xyz = m * sRGB
        dMtx[0] = 3.2410;
        dMtx[1] = -1.5374;
        dMtx[2] = -0.4986;
        dMtx[3] = -0.9692;
        dMtx[4] = 1.8760;
        dMtx[5] = 0.0416;
        dMtx[6] = 0.0556;
        dMtx[7] = -0.2040;
        dMtx[8] = 1.0570;
    }

    fun forward(tristimulus: Tristimulus): Color? {
        val triple = multiply(tristimulus.X / 100.0, tristimulus.Y / 100.0, tristimulus.Z / 100.0)
        var linear = arrayListOf<Double>(triple.first, triple.second, triple.third)

        for (i in 0 until 3) {
            linear[i] =
                if (linear[i] <= 0.0031308) {
                    12.92 * linear[i]
                } else {
                    1.055 * Math.pow(linear[i], (1.0 / 2.4)) - .055
                }
        }
        val R = linear[0] * 255.0;
        val G = linear[1] * 255.0;
        val B = linear[2] * 255.0;

        val r = requant(R.toFloat())
        val g = requant(G.toFloat())
        val b = requant(B.toFloat())
        return Color.valueOf(r, g, b)
    }

    fun requant(n: Float): Float {
        if (n < 0F) return 0F;
        else if (n > 255) return 255F;
        else return n;
    }
}