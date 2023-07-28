package com.ctyeung.colorbration.data

// Reference:	http://en.wikipedia.org/wiki/SRGB#The_forward_transformation_.28CIE_xyY_or_CIE_XYZ_to_sRGB.29
class XYZ2sRGB : Matrix3x3() {
    var X: Double = 0.0
    var Y: Double = 0.0
    var Z: Double = 0.0
    var R: Double = 0.0
    var G: Double = 0.0
    var B: Double = 0.0

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

    override fun empty() {
        super.empty();
        X = 0.0
        Y = 0.0
        Z = 0.0
        R = 0.0
        G = 0.0
        B = 0.0
    }

    override fun isEmpty(): Boolean {
        if (X == 0.0 && Y == 0.0 && Z == 0.0 && R == 0.0 && G == 0.0 && B == 0.0)
            return true;
        return false;
    }

    fun forward(): Boolean {
        if (!multiply(X / 100.0, Y / 100.0, Z / 100.0))
            return false;

        var linear = arrayListOf<Double>(0.0, 0.0, 0.0)
        linear[0] = out1;
        linear[1] = out2;
        linear[2] = out3;

        for (i in 0 until 3) {
            linear[i] =
                if (linear[i] <= 0.0031308) {
                    12.92 * linear[i]
                } else {
                    1.055 * Math.pow(linear[i], (1.0 / 2.4)) - .055
                }
        }
        R = linear[0] * 255.0;
        G = linear[1] * 255.0;
        B = linear[2] * 255.0;

        R = requant(R);
        G = requant(G);
        B = requant(B);
        return true;
    }

    fun requant(n: Double): Double {
        if (n < 0.0) return 0.0;
        else if (n > 255) return 255.0;
        else return n;
    }
}