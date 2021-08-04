package ru.myitschool.nasa_bootcamp.lookbeyond.maths

import kotlin.math.*


/**
Изучалось через сайт http://ssd.jpl.nasa.gov/?planet_pos
ОБЪЕДИНИТЬ С КЛАССОМ RaRec!!!
 */
class OrbitalElements(// Mean distance
    _axis: Double, // Eccentricity of orbit
    _eccentricity: Double, // Inclination of orbit
    _inclination: Double, // Longitude of ascending node
    _longitude: Double, // Longitude of perihelion
    _perihelion: Double, // Mean longitude
    _meanLongitude: Double
) {
    var axis: Double? = null
    var eccentricity: Double? = null
    var inclination: Double? = null
    var longitude: Double? = null
    var perihelion: Double? = null
    var meanAnomaly: Double? = null

    init {
        axis = _axis
        eccentricity = _eccentricity
        inclination = _inclination
        longitude = _longitude
        perihelion = _perihelion
        meanAnomaly = _meanLongitude
    }

    private val E =
        meanAnomaly!! + eccentricity!! * (180 / Math.PI) * Math.sin(meanAnomaly!!.toDouble()) * (1.0 + eccentricity!! * Math.cos(
            meanAnomaly!!.toDouble()
        ))
    //or  E = M + e * sin(M) * ( 1.0 + e * cos(M) ) in radians

    private val xv = cos(E) - eccentricity!!
    private val yv = sqrt(1.0 - eccentricity!! * eccentricity!!) * Math.sin(E)

    val v = atan2(yv, xv)

    val anomaly: Double
        get() = calculateTrueAnomaly(meanAnomaly!! - perihelion!!, eccentricity!!)


    companion object {

        // calculation error
        private const val EPSILON = 1.0e-6f

        //Вычислить true nomaly из mean anomaly
        private fun calculateTrueAnomaly(meanAnomaly: Double, e: Double): Double {

            var _yv = meanAnomaly + e * sin(meanAnomaly) * (1.0 + e * cos(meanAnomaly))
            var e1: Double

            do {
                e1 = _yv
                _yv = e1 - (e1 - e * sin(e1) - meanAnomaly) / (1.0 - e * cos(e1))

            } while (abs(_yv - e1) > EPSILON)

            // convert eccentric anomaly to true anomaly
            val v = 2f * atan(
                sqrt((1 + e) / (1 - e))
                        * tan(0.5f * _yv)
            )
            return mod(v)
        }
    }
}
