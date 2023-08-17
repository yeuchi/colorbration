package com.ctyeung.colorbration.data.math

open class Matrix3x3 {

    var dMtx = DoubleArray(9)
    var iMtx = ArrayList<Double>()

    fun multiply(
        in1: Double,
        in2: Double,
        in3: Double
    ): Triple<Double, Double, Double> {
        val out1 = in1 * dMtx[0] + in2 * dMtx[1] + in3 * dMtx[2];
        val out2 = in1 * dMtx[3] + in2 * dMtx[4] + in3 * dMtx[5];
        val out3 = in1 * dMtx[6] + in2 * dMtx[7] + in3 * dMtx[8];
        return Triple(out1, out2, out3);
    }

    fun inverse(): Boolean {
        var det: Number;
        det = dMtx[0] * (dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7])
        -dMtx[1] * (dMtx[3] * dMtx[8] - dMtx[5] * dMtx[6])
        +dMtx[2] * (dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6]);

        iMtx[0] = (dMtx[4] * dMtx[8] - dMtx[5] * dMtx[7]) / det;
        iMtx[3] = (dMtx[6] * dMtx[5] - dMtx[3] * dMtx[8]) / det;
        iMtx[6] = (dMtx[3] * dMtx[7] - dMtx[4] * dMtx[6]) / det;
        iMtx[1] = (dMtx[2] * dMtx[7] - dMtx[1] * dMtx[8]) / det;
        iMtx[4] = (dMtx[0] * dMtx[8] - dMtx[2] * dMtx[6]) / det;
        iMtx[7] = (dMtx[1] * dMtx[6] - dMtx[0] * dMtx[7]) / det;
        iMtx[2] = (dMtx[1] * dMtx[5] - dMtx[2] * dMtx[4]) / det;
        iMtx[5] = (dMtx[2] * dMtx[3] - dMtx[0] * dMtx[5]) / det;
        iMtx[8] = (dMtx[0] * dMtx[4] - dMtx[1] * dMtx[3]) / det;

        return true;
    }
}