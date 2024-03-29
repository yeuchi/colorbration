package com.ctyeung.colorbration.data.ref

object Illuminants {
    const val ILLUMINANT_A = "illuminant A"
    const val ILLUMINANT_B = "illuminant B"
    const val ILLUMINANT_C = "illuminant C"
    const val ILLUMINANT_D50 = "illuminant D50"
    const val ILLUMINANT_D65 = "illuminant D65"

    fun retrieve(type: String): List<Double> {
        return when (type) {
            ILLUMINANT_B -> illuminantB
            ILLUMINANT_C -> illuminantC
            ILLUMINANT_D50 -> illuminantD50
            ILLUMINANT_D65 -> illuminantD65

//            ILLUMINANT_A,
            else -> illuminantA
        }
    }

    val illuminantA = listOf<Double>(
        14.71, 17.68, 20.99, 24.67, 28.70,
        33.09, 37.81, 42.87, 48.24, 53.91,
        59.86, 66.06, 72.50, 79.13, 85.95,
        92.91, 100.00, 107.18, 114.44, 121.73,
        129.04, 136.35, 143.62, 150.84, 157.98,
        165.03, 171.96, 178.77, 185.43, 191.93, 198.26
    )

    val illuminantB = listOf<Double>(
        41.30, 52.10, 63.20, 73.10, 80.80,
        85.40, 88.30, 92.00, 95.20, 96.50,
        94.20, 90.70, 89.50, 92.20, 96.90,
        101.00, 102.80, 102.60, 101.00, 98.20,
        98.00, 98.50, 99.70, 101.0, 102.20,
        103.90, 105.0, 104.90, 103.90, 101.60, 99.10
    )

    val illuminantC = listOf<Double>(
        63.30, 80.60, 98.10, 112.40, 121.50,
        124.0, 123.1, 123.8, 123.9, 120.7,
        112.1, 102.3, 96.9, 98.0, 102.1,
        105.2, 105.3, 102.3, 97.8, 93.2,
        89.7, 88.4, 88.1, 88.0, 87.8,
        88.2, 87.9, 86.3, 84.0, 80.2, 76.3
    )

    val illuminantD50 = listOf<Double>(
        49.31, 56.51, 60.03, 57.82, 74.82,
        87.25, 90.61, 91.37, 95.11, 91.96,
        95.72, 96.61, 97.13, 102.1, 100.75,
        102.32, 100.0, 97.74, 98.92, 93.5,
        97.69, 99.27, 99.04, 95.72, 98.86,
        95.67, 98.19, 103.0, 99.13, 87.38, 91.60
    )

    val illuminantD65 = listOf<Double>(
        82.75, 91.49, 93.43, 86.68, 104.86,
        117.01, 117.81, 114.86, 115.92, 108.81,
        109.35, 107.80, 104.79, 107.69, 104.41,
        104.05, 100.0, 96.33, 95.79, 88.69,
        90.01, 89.60, 87.70, 83.29, 83.70,
        80.03, 80.21, 82.28, 78.28, 69.72, 71.61
    );
}