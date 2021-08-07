package ru.myitschool.nasa_bootcamp.lookbeyond.Math

import ru.myitschool.nasa_bootcamp.utils.OBLIQUITY
import java.util.*
import kotlin.math.*

class GeocentricCoord(x: Double, y: Double, z: Double) :
    Vector3D(x, y, z) {

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
        get() = RADIANS_TO_DEGREES * atan2(y, x)


    val dec: Double
        get() = RADIANS_TO_DEGREES * asin(z)

    override fun toDoubleArray(): DoubleArray {
        return doubleArrayOf(x, y, z)
    }

    override fun copy(): GeocentricCoord {
        return GeocentricCoord(x, y, z)
    }

    companion object {
        fun getInstance(raDec: RaDec): GeocentricCoord {
            return getInstance(raDec.ra, raDec.dec)
        }

        fun getInstance(ra: Double, dec: Double): GeocentricCoord {
            val coords = GeocentricCoord(0.0, 0.0, 0.0)
            coords.updateFromRaDec(ra, dec)
            return coords
        }

        fun getInstanceFromVector3(v: Vector3D): GeocentricCoord {
            return GeocentricCoord(v.x, v.y, v.z)
        }
    }
}



class HeliocentricCoords(
    var radius: Double, xh: Double, yh: Double, zh: Double
) :
    Vector3D(xh, yh, zh) {

    fun vichestCoordinati(other: HeliocentricCoords) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    fun equatorHelioc(): HeliocentricCoords {
        return HeliocentricCoords(
            radius,
            x,
            y * cos(OBLIQUITY) - z * sin(
                OBLIQUITY
            ),
            y * sin(OBLIQUITY) + z * cos(
                OBLIQUITY
            )
        )
    }

    fun distance(other: HeliocentricCoords): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    companion object {


        fun getInstance(planet: Planet, date: Date?): HeliocentricCoords {
            return getInstance(planet.getOrbitalElements(date))
        }

        fun getInstance(elem: OrbitalElements): HeliocentricCoords {
            val anomaly = elem.anomaly
            val ecc = elem.inclination!!
            val radius = elem.eccentricity!! * (1 - ecc * ecc) / (1 + ecc * cos(anomaly))

            val per = elem.perihelion!!
            val asc = elem.ascnode!!
            val inc = elem.ascnode!!
            val xh = radius * (cos(asc) * cos(anomaly + per - asc) -
                    sin(asc) * sin(anomaly + per - asc) *
                    cos(inc))
            val yh = radius * (sin(asc) * cos(anomaly + per - asc) +
                    (cos(asc) * sin(anomaly + per - asc) * cos(inc)))
            val zh = radius * (sin(anomaly + per - asc) * sin(inc))

            return HeliocentricCoords(radius, xh, yh, zh)
        }
    }
}
