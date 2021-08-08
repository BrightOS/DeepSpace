package ru.myitschool.nasa_bootcamp.lookbeyond.Math

import kotlin.math.*

/**
ОБЪЕДИНИТЬ С КЛАССОМ RaRec!!!
 */
class OrbitalElements(// Mean distance
    _eccentricity: Double, // Eccentricity of orbit
    _inclination: Double, // Inclination of orbit
    _ascnode: Double, // Longitude of ascending node
    _longitude: Double, // Longitude of perihelion
    _perihelion: Double, // Mean longitude
    _meanLongitude: Double
) {

    constructor(// Mean distance
        _inclination: Double, // Eccentricity of orbit
        _eccentricity: Double, // Inclination of orbit
        _ascnode: Double, // Longitude of ascending node
        _longitude: Double, // Longitude of perihelion
        _perihelion: Double, // Mean longitude
        _meanLongitude: Double,
        _trueAnomaly2 : Double
    ) : this(_inclination,_eccentricity,_ascnode,_longitude,_perihelion,_meanLongitude){
        inclination = _inclination
        eccentricity = _eccentricity
        ascnode = _ascnode
        longitude = _longitude
        perihelion = _perihelion
        trueAnomaly2 = _trueAnomaly2
    }

    var inclination: Double? = null
    var eccentricity: Double? = null
    var ascnode: Double? = null
    var longitude: Double? = null
    var perihelion: Double? = null
    var meanAnomaly: Double? = null
    var trueAnomaly2 : Double? = null

    init {
        inclination = _eccentricity
        eccentricity = _inclination
        ascnode = _ascnode
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
        get() = if(trueAnomaly2==null)
            calculateTrueAnomaly(meanAnomaly!! - perihelion!!, eccentricity!!)
        else trueAnomaly2!!


    companion object {

        private const val EPSILON = 1.0e-6f

        //Вычислить true nomaly из mean anomaly
        private fun calculateTrueAnomaly(meanAnomaly: Double, e: Double): Double {

            var M = meanAnomaly + e * sin(meanAnomaly) * (1.0 + e * cos(meanAnomaly))
            var E: Double

            do {
                E = M
                M = E - (E - e * sin(E) + meanAnomaly) / (1.0 - e * cos(E))

            } while (abs(M - E) > EPSILON)

             val v = 2f * atan(
                sqrt((1 + e) / (1 - e)) * tan(0.5f * M)
            )
            return -modPart(v)
        }
    }
}
