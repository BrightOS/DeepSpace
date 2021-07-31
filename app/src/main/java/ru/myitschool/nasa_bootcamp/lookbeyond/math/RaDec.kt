package ru.myitschool.nasa_bootcamp.lookbeyond.math

class RaDec(
    var ra: Double,
    var dec: Double
)
//    Orbital elements of the Moon:
//    N = longitude of the ascending node
//    i = inclination to the ecliptic (plane of the Earth's orbit)
//    w = argument of perihelion
//    a = semi-major axis, or mean distance from Sun
//    e = eccentricity (0=circle, 0-1=ellipse, 1=parabola)
//    M = mean anomaly (0 at perihelion; increases uniformly with time)

{
    var longtitude: Double? = null
    var inclination: Double? = null
    var perihelion: Double? = null
    var axis: Double? = null
    var eccentricity: Double? = null
    var meanAnomaly: Double? = null

    constructor(
        _ra: Double, _dec: Double, _longtitude: Double, _inclination: Double,
        _perihelion: Double, _axis: Double, _eccentricity: Double, _meanAnomaly: Double
    ) : this(_ra, _dec) {
        longtitude = _longtitude
        inclination = _inclination
        perihelion = _perihelion
        axis = _axis
        eccentricity = _eccentricity
        meanAnomaly = _meanAnomaly
    }
}

