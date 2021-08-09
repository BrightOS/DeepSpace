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
        _eccentricity: Double, // Eccentricity of orbit
        _inclination: Double, // Inclination of orbit
        _ascnode: Double, // Longitude of ascending node
        _longitude: Double, // Longitude of perihelion
        _perihelion: Double, // Mean longitude
        _meanLongitude: Double,
        _trueAnomaly2 : Double
    ) : this(_eccentricity,_inclination,_ascnode,_longitude,_perihelion,_meanLongitude){
        eccentricity = _eccentricity
        inclination = _inclination
        ascnode = _ascnode
        longitude = _longitude
        perihelion = _perihelion
        trueAnomaly2 = _trueAnomaly2
    }

    var eccentricity: Double? = null
    var inclination: Double? = null
    var ascnode: Double? = null
    var longitude: Double? = null
    var perihelion: Double? = null
    var meanAnomaly: Double? = null
    var trueAnomaly2 : Double? = null

    init {
        eccentricity = _eccentricity
        inclination = _inclination
        ascnode = _ascnode
        longitude = _longitude
        perihelion = _perihelion
        meanAnomaly = _meanLongitude
    }

    private val E =
        meanAnomaly!! + inclination!! * (180 / Math.PI) * Math.sin(meanAnomaly!!.toDouble()) * (1.0 + inclination!! * Math.cos(
            meanAnomaly!!.toDouble()
        ))

    private val xv = cos(E) - inclination!!
    private val yv = sqrt(1.0 - inclination!! * inclination!!) * Math.sin(E)

    val v = atan2(yv, xv)

    val anomaly: Double
        get() = if(trueAnomaly2==null)
            calculateTrueAnomaly(meanAnomaly!! - perihelion!!, inclination!!)
        else trueAnomaly2!!


    companion object {

        private const val EPSILON = 1.0e-6f

        //Вычислить true nomaly из mean anomaly
        private fun calculateTrueAnomaly(meanAnomaly: Double, ecc: Double): Double {

            var E = meanAnomaly + ecc * sin(meanAnomaly) * (1.0 + ecc * cos(meanAnomaly))
            var M: Double

            do {
                M = E
                E = M - (M - ecc * sin(M) + meanAnomaly) / (1.0 - ecc * cos(M))

            } while (abs(E - M) > EPSILON)

             val v = 2f * atan(sqrt((1 + ecc) / (1 - ecc)) * tan(0.5f * E)
            )
            return modPart(v)
        }
    }
}
