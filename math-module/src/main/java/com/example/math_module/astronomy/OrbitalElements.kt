package com.example.math_module.astronomy

import ru.berserkers.deepspace.navigator.maths.mod
import kotlin.math.*

class OrbitalElements(
    val eccentricity: Double,
    val inclination: Double,
    val ascnode: Double,
    val longitude: Double,
    val perihelion: Double,
    private val meanLongitude: Double,
    val trueAnomaly2: Double? = null,
) {
    val anomaly: Double
        get() = trueAnomaly2 ?: calculateTrueAnomaly(meanLongitude - perihelion, inclination)

    private fun calculateTrueAnomaly(meanAnomaly: Double, ecc: Double): Double {
        val EPS = 1.0e-6
        var E = meanAnomaly + ecc * sin(meanAnomaly) * (1.0f + ecc * cos(meanAnomaly))
        var M: Double

        var counter = 0
        do {
            M = E
            E = M - (M - ecc * sin(M) - meanAnomaly) / (1.0f - ecc * cos(M))
            if (counter++ > 100) {
                break
            }
        } while (abs(E - M) > EPS)

        val v = 2f * atan(sqrt((1 + ecc) / (1 - ecc)) * tan(0.5f * E))
        return mod(v)
    }
}
