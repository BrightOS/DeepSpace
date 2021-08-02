package ru.myitschool.nasa_bootcamp.lookbeyond.math.astronomy

import ru.myitschool.nasa_bootcamp.lookbeyond.math.DEGREES_TO_RADIANS
import ru.myitschool.nasa_bootcamp.lookbeyond.math.RADIANS_TO_DEGREES
import ru.myitschool.nasa_bootcamp.lookbeyond.math.RaDec

// Местоположение тела в евклидовом простр
class GeocentricCoord(x: Double, y: Double, z: Double) :
    Vector3D(x, y, z) {
    //Обновить координаты с помощью ra и dec
    fun updateFromRaDec(ra: Double, dec: Double) {
        x = Math.cos(ra * DEGREES_TO_RADIANS) * Math.cos(dec * DEGREES_TO_RADIANS)
        y = Math.sin(ra * DEGREES_TO_RADIANS) * Math.cos(dec * DEGREES_TO_RADIANS)
        z = Math.sin(dec * DEGREES_TO_RADIANS)
    }// Assumes unit sphere.

    //RA и DEC в градусы
    val ra: Double
        get() = RADIANS_TO_DEGREES * Math.atan2(y, x)

    val dec: Double
        get() =  RADIANS_TO_DEGREES * Math.asin(z)


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