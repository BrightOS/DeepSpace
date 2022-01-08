 package ru.berserkers.deepspace.maths.coords

import ru.berserkers.deepspace.maths.astronomy.RaDec
import ru.berserkers.deepspace.maths.matrix.Vector3D
import ru.berserkers.deepspace.navigator.maths.DEGREES_TO_RADIANS
import ru.berserkers.deepspace.navigator.maths.RADIANS_TO_DEGREES
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
 
 //расположение тела в евклидовом пространстве, когда проектируется в сферху (Земля в центре)
class GeocentricCoordinates(x: Double, y: Double, z: Double) : Vector3D(x, y, z) {
    fun updateFromRaDec(raDec: RaDec) {
        updateFromRaDec(raDec.ra, raDec.dec)
    }

    private fun updateFromRaDec(ra: Double, dec: Double) {
        val raRadians = ra * DEGREES_TO_RADIANS
        val decRadians = dec * DEGREES_TO_RADIANS
        x = cos(raRadians) * cos(decRadians)
        y = sin(raRadians) * cos(decRadians)
        z = sin(decRadians)
    }

    val ra: Double
        get() =  RADIANS_TO_DEGREES * atan2(y, x)

    val dec: Double
        get() = RADIANS_TO_DEGREES * asin(z)

    override fun toFloatArray(): FloatArray {
        return floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat())
    }

    companion object {
        fun getInstance(raDec: RaDec): GeocentricCoordinates {
            return getInstance(raDec.ra, raDec.dec)
        }

        fun getInstance(ra: Double, dec: Double): GeocentricCoordinates {
            val coords = GeocentricCoordinates(0.0, 0.0, 0.0)
            coords.updateFromRaDec(ra, dec)
            return coords
        }
    }
}
