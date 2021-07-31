package ru.myitschool.nasa_bootcamp.lookbeyond.math

import ru.myitschool.nasa_bootcamp.lookbeyond.math.TimeMachine.julianCenturies
import java.util.*

class Planet {


}

fun getLocationViaOrbitalElements(
    time: Date?, _longtitude: Double,
    _inclination: Double, _perihelion: Double,
    _axis: Double, _eccentricity: Double, _meanAnomaly: Double
): RaDec {
    val t = julianCenturies(time)

//    Orbital elements of the Moon:
//    N = longitude of the ascending node
//    i = inclination to the ecliptic (plane of the Earth's orbit)
//    w = argument of perihelion
//    a = semi-major axis, or mean distance from Sun
//    e = eccentricity (0=circle, 0-1=ellipse, 1=parabola)
//    M = mean anomaly (0 at perihelion; increases uniformly with time)

    var longtitude = _longtitude * t
    var inclination = _inclination
    val perihelion = _perihelion * t
    var axis = _axis
    val eccentricity = _eccentricity
    val meanAnomaly = _meanAnomaly * t


    val E =
        meanAnomaly + eccentricity * (180 / Math.PI) * Math.sin(meanAnomaly.toDouble()) * (1.0 + eccentricity * Math.cos(
            meanAnomaly.toDouble()
        ))
    //or  E = M + e * sin(M) * ( 1.0 + e * cos(M) ) in radians

    val xv = Math.cos(E) - eccentricity
    val yv = Math.sqrt(1.0 - eccentricity * eccentricity) * Math.sin(E)

    val v = Math.atan2(yv, xv)
    val r = Math.sqrt(xv * xv + yv * yv)

    val lonsun = v + perihelion

    val xs = r * Math.cos(lonsun)
    val ys = r * Math.sin(lonsun)

    val ecl = 23.4393 - 3.563E-7 * t

    val xe = xs
    val ye = ys * Math.cos(ecl)
    val ze = ys * Math.sin(ecl)

    val ra = Math.atan2(ye, xe)
    val dec = Math.atan2(ze, Math.sqrt(xe * xe + ye * ye))

    return RaDec(ra.toFloat(), dec.toFloat())
}


//N = 125.1228 - 0.0529538083 * d
//i = 5.1454
//w = 318.0634 + 0.1643573223 * d
//a = 60.2666  (Earth radii)
//e = 0.054900
//M = 115.3654 + 13.0649929509 * d

fun getMoonLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 125.1228 - 0.0529538083, 5.1454, 318.0634 + 0.1643573223,
        60.2666, 0.054900, 115.3654 + 13.0649929509
    )
}


//N =  48.3313 + 3.24587E-5 * d
//i = 7.0047 + 5.00E-8 * d
//w =  29.1241 + 1.01444E-5 * d
//a = 0.387098  (AU)
//e = 0.205635 + 5.59E-10 * d
//M = 168.6562 + 4.0923344368 * d

fun getMercuryLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time,  48.3313 + 3.24587E-5, 7.0047 + 5.00E-8, 29.1241 + 1.01444E-5,
        0.387098, 0.205635 + 5.59E-10 , 168.6562 + 4.0923344368
    )
}

//Orbital elements of the Sun:
//
//N = 0.0
//i = 0.0
//w = 282.9404 + 4.70935E-5 * d
//a = 1.000000  (AU)
//e = 0.016709 - 1.151E-9 * d
//M = 356.0470 + 0.9856002585 * d

fun getSunLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time,   0.0, 0.0, 282.9404 + 4.70935E-5,
        1.000000 , 0.016709 - 1.151E-9 , 356.0470 + 0.9856002585
    )
}