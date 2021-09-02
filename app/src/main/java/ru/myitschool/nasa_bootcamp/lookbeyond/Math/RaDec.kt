package ru.myitschool.nasa_bootcamp.lookbeyond.Math

 import ru.myitschool.nasa_bootcamp.utils.RADIANS_TO_DEGREES
 import java.util.*
import kotlin.math.*


class RaDec(
    var ra: Double,
    var dec: Double
) {

    var longtitude : Double? = null
    var inclination: Double? = null
    var perihelion: Double? = null
    var axis: Double? = null
    private var eccentricity: Double? = null
    private var meanAnomaly: Double? = null
    private var trueAnomaly: Double? = null

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

        val xv = cos(E) - eccentricity!!
        val yv = sqrt(1.0 - eccentricity!! * eccentricity!!) * Math.sin(E)

        val v = atan2(yv, xv)

        trueAnomaly = trueAnomaly((meanAnomaly!! - perihelion!!), eccentricity!!)
    }

    companion object {
        private const val eps = 1.0e-5f

        // m - mean anomaly in radians
        // e - orbit eccentricity
        private fun trueAnomaly(meanAnomaly: Double, e: Double): Double {
            var _eccentric0 = meanAnomaly + e * sin(meanAnomaly) * (1.0f + e * cos(meanAnomaly))
            var eccentric: Double

            do {
                eccentric = _eccentric0
                _eccentric0 = eccentric - (eccentric - e * sin(eccentric) - meanAnomaly) / (1.0f - e * cos(eccentric))

            } while (abs(_eccentric0 - eccentric) > eps)

            // convert eccentric anomaly to true anomaly
            val v = 2f * atan(sqrt((1 + e) / (1 - e)) * tan(0.5f * _eccentric0))
            return modPart(v)
        }

        fun calculateRaDecDist(coords: HeliocentricCoords): RaDec {
             val ra =
                modPart(atan2(coords.y, coords.x)) * RADIANS_TO_DEGREES
            val dec =
                (atan(coords.z / sqrt(coords.x * coords.x + coords.y * coords.y))
                        * RADIANS_TO_DEGREES)
            return RaDec(ra, dec)
        }

        fun getInstance(
            planet: Planet, time: Date?,
            earthCoords: HeliocentricCoords
        ): RaDec {
             if (planet == Planet.Moon) {
                return Planet.getMoonLocation1(time)
            }
            val coords: HeliocentricCoords?
            if (planet == Planet.Sun) {

                coords = HeliocentricCoords(
                    earthCoords.radius, earthCoords.x * -1.0,
                    earthCoords.y * -1.0, earthCoords.z * -1.0
                )
            } else {
                coords = HeliocentricCoords.getInstance(planet, time)
                coords.vichestCoordinati(earthCoords)
            }
            val equ = coords.equatorHelioc()
            return calculateRaDecDist(equ)
        }

        fun getInstance(coords: GeocentricCoord): RaDec {
            var raRad = atan2(coords.y, coords.x)
            if (raRad < 0) raRad += PI*2
            val decRad = atan2(coords.z, sqrt(coords.x * coords.x + coords.y * coords.y))
            return RaDec(
                raRad * RADIANS_TO_DEGREES,
                decRad * RADIANS_TO_DEGREES
            )
        }
    }
}