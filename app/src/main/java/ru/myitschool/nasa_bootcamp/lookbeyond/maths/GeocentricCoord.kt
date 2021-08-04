package ru.myitschool.nasa_bootcamp.lookbeyond.maths


 import java.util.*


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

    }
}

class HeliocentricCoords(// Radius
    var radius: Double, xh: Double, yh: Double, zh: Double
) :
    Vector3D(xh, yh, zh) {

    fun Subtract(other: HeliocentricCoords) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    fun CalculateEquatorialCoordinates(): HeliocentricCoords {
        return HeliocentricCoords(
            radius,
            x,
            y * Math.cos(OBLIQUITY) - z * Math.sin(OBLIQUITY),
            y * Math.sin(OBLIQUITY) + z * Math.cos(OBLIQUITY)
        )
    }

    fun DistanceFrom(other: HeliocentricCoords): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return Math.sqrt(dx * dx + dy * dy + dz * dz)
    }



//    The position in space
//    Compute the planet's position in 3-dimensional space:
//
//    xh = r * ( cos(N) * cos(v+w) - sin(N) * sin(v+w) * cos(i) )
//    yh = r * ( sin(N) * cos(v+w) + cos(N) * sin(v+w) * cos(i) )
//    zh = r * ( sin(v+w) * sin(i) )
//    For the Moon, this is the geocentric (Earth-centered) position in the ecliptic coordinate system. For the planets, this is the heliocentric (Sun-centered) position, also in the ecliptic coordinate system. If one wishes, one can compute the ecliptic longitude and latitude (this must be done if one wishes to correct for perturbations, or if one wants to precess the position to a standard epoch):
//
//    lonecl = atan2( yh, xh )
//    latecl = atan2( zh, sqrt(xh*xh+yh*yh) )
//    As a check one can compute sqrt(xh*xh+yh*yh+zh*zh), which of course should equal r (except for small round-off errors).

    companion object {
        // Value of the obliquity of the ecliptic for J2000
        private val OBLIQUITY = 23.439281f * DEGREES_TO_RADIANS
        fun getInstance(planet: Planet, date: Date?): HeliocentricCoords {
            return getInstance(planet.getOrbitalElements(date))
        }

        fun getInstance(elem: OrbitalElements): HeliocentricCoords {
            val anomaly = elem.anomaly
            val ecc = elem.eccentricity
            val radius = elem.axis!! * (1 - ecc!! * ecc) / (1 + ecc * Math.cos(anomaly))

            // heliocentric rectangular coordinates of planet
            val per = elem.perihelion
            val asc = elem.longitude
            val inc = elem.inclination

            val xh = radius *
                    (Math.cos(asc!!) * Math.cos(anomaly + per!! - asc) -
                            Math.sin(asc) * Math.sin(anomaly + per - asc) *
                            Math.cos(inc!!))
            val yh = radius *
                    (Math.sin(asc) * Math.cos(anomaly + per - asc) +
                            (Math.cos(asc) * Math.sin(anomaly + per - asc) *
                                    Math.cos(inc)))
            val zh = radius * (Math.sin(anomaly + per - asc) * Math.sin(inc))
            return HeliocentricCoords(radius, xh, yh, zh)
        }
    }
}