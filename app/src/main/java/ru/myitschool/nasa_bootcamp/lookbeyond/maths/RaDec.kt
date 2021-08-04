package ru.myitschool.nasa_bootcamp.lookbeyond.maths


import java.util.*


////    Любая точка небесного экватора имеет склонение 0°;
////    Склонение северного полюса мира равно +90°;
////    Склонение южного полюса мира равно −90°.


//В градусах
class RaDec(
    var ra: Double, //
    var dec: Double

    //    Orbital elements of the Moon:
//    N = longitude of the ascending node
//    i = inclination to the ecliptic (plane of the Earth's orbit)
//    w = argument of perihelion
//    a = semi-major axis, or mean distance from Sun
//    e = eccentricity (0=circle, 0-1=ellipse, 1=parabola)
//    M = mean anomaly (0 at perihelion; increases uniformly with time)


) {
    var longtitude : Double? = null
    var inclination: Double? = null
    var perihelion: Double? = null
    var axis: Double? = null
    var eccentricity: Double? = null
    var meanAnomaly: Double? = null
    var trueAnomaly: Double? = null

    constructor( _ra: Double,  _dec: Double, _longtitude: Double, _inclination: Double,
                 _perihelion : Double, _axis : Double, _eccentricity : Double, _meanAnomaly: Double  )
            : this(_ra , _dec ){
        longtitude = _longtitude
        inclination = _inclination
        perihelion = _perihelion
        axis = _axis
        eccentricity = _eccentricity
        meanAnomaly = _meanAnomaly

        val E = meanAnomaly!! + eccentricity!! * (180 / Math.PI) * Math.sin(meanAnomaly!!.toDouble()) * (1.0 + eccentricity!! * Math.cos(
            meanAnomaly!!.toDouble()
        ))
        //or  E = M + e * sin(M) * ( 1.0 + e * cos(M) ) in radians

        val xv = Math.cos(E) - eccentricity!!
        val yv = Math.sqrt(1.0 - eccentricity!! * eccentricity!!) * Math.sin(E)

        val v = Math.atan2(yv, xv)

        trueAnomaly = trueAnomaly((meanAnomaly!! - perihelion!!), eccentricity!!).toDouble()
    }

    companion object {
        private const val eps = 1.0e-6f

        // compute the true anomaly from mean anomaly using iteration
        // m - mean anomaly in radians
        // e - orbit eccentricity
        // Return value is in radians.
        private fun trueAnomaly(meanAnomaly: Double, e: Double): Double {
            // initial approximation of eccentric anomaly
            var _eccentric0 = meanAnomaly + e * Math.sin(meanAnomaly) * (1.0f + e * Math.cos(meanAnomaly))
            var eccentric: Double

            do {
                eccentric = _eccentric0
                _eccentric0 = eccentric - (eccentric - e * Math.sin(eccentric) - meanAnomaly) / (1.0f - e * Math.cos(eccentric))

            } while (Math.abs(_eccentric0 - eccentric) > eps)

            // convert eccentric anomaly to true anomaly
            val v = 2f * Math.atan(Math.sqrt((1 + e) / (1 - e)) * Math.tan(0.5f * _eccentric0))
            return mod(v)
        }


        fun raDecDistance(coords: HeliocentricCoords): RaDec {
            // Найти Ra и Dec из экваториальной системы
            val ra = mod(Math.atan2(coords.y, coords.x)) * RADIANS_TO_DEGREES
            val dec =
                (Math.atan(coords.z / Math.sqrt(coords.x.toDouble() * coords.x + coords.y * coords.y)) * RADIANS_TO_DEGREES)
            return RaDec(ra, dec)
        }

        fun getInstance(
            planet: Planet, time: Date?,
            earthCoords: HeliocentricCoords
        ): RaDec {
            if (planet == Planet.Moon) {
                return getMoonLocation(time)
            }
            var coords: HeliocentricCoords? = null

            if (planet == Planet.Sun) {
                coords = HeliocentricCoords(
                    earthCoords.radius, earthCoords.x * -1.0,
                    earthCoords.y * -1.0, earthCoords.z * -1.0
                )
            } else {
                coords = HeliocentricCoords.getInstance(planet, time)
                coords.Subtract(earthCoords)
            }
            val equ = coords.CalculateEquatorialCoordinates()
            return raDecDistance(equ)
        }


    }
}