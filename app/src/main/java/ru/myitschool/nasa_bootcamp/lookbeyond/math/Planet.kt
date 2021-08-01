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

    return RaDec(ra , dec )
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
        time, 48.3313 + 3.24587E-5, 7.0047 + 5.00E-8, 29.1241 + 1.01444E-5,
        0.387098, 0.205635 + 5.59E-10, 168.6562 + 4.0923344368
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
        time, 0.0, 0.0, 282.9404 + 4.70935E-5,
        1.000000, 0.016709 - 1.151E-9, 356.0470 + 0.9856002585
    )
}


//Orbital elements of Venus:
//
//N =  76.6799 + 2.46590E-5 * d
//i = 3.3946 + 2.75E-8 * d
//w =  54.8910 + 1.38374E-5 * d
//a = 0.723330  (AU)
//e = 0.006773 - 1.302E-9 * d
//M =  48.0052 + 1.6021302244 * d

fun getVenusLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 76.6799 + 2.46590E-5, 3.3946 + 2.75E-8 , 54.8910 + 1.38374E-5,
        0.723330, 0.006773 - 1.302E-9, 48.0052 + 1.6021302244
    )
}

//Orbital elements of Mars:
//
//N =  49.5574 + 2.11081E-5 * d
//i = 1.8497 - 1.78E-8 * d
//w = 286.5016 + 2.92961E-5 * d
//a = 1.523688  (AU)
//e = 0.093405 + 2.516E-9 * d
//M =  18.6021 + 0.5240207766 * d

fun getMarsLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 49.5574 + 2.11081E-5, 1.8497 - 1.78E-8 , 286.5016 + 2.92961E-5,
        1.523688, 0.093405 + 2.516E-9, 18.6021 + 0.5240207766
    )
}

//Orbital elements of Jupiter:
//
//N = 100.4542 + 2.76854E-5 * d
//i = 1.3030 - 1.557E-7 * d
//w = 273.8777 + 1.64505E-5 * d
//a = 5.20256  (AU)
//e = 0.048498 + 4.469E-9 * d
//M =  19.8950 + 0.0830853001 * d

fun getJupiterLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 100.4542 + 2.76854E-5, 1.3030 - 1.557E-7 , 273.8777 + 1.64505E-5,
        5.20256, 0.048498 + 4.469E-9, 19.8950 + 0.0830853001
    )
}

//Orbital elements of Saturn:
//
//N = 113.6634 + 2.38980E-5 * d
//i = 2.4886 - 1.081E-7 * d
//w = 339.3939 + 2.97661E-5 * d
//a = 9.55475  (AU)
//e = 0.055546 - 9.499E-9 * d
//M = 316.9670 + 0.0334442282 * d
fun getSaturnLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 113.6634 + 2.38980E-5, 2.4886 - 1.081E-7 , 339.3939 + 2.97661E-5,
        9.55475, 0.055546 - 9.499E-9, 316.9670 + 0.0334442282
    )
}

//Orbital elements of Uranus:
//
//N =  74.0005 + 1.3978E-5 * d
//i = 0.7733 + 1.9E-8 * d
//w =  96.6612 + 3.0565E-5 * d
//a = 19.18171 - 1.55E-8 * d  (AU)
//e = 0.047318 + 7.45E-9 * d
//M = 142.5905 + 0.011725806 * d

fun getUranusLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 74.0005 + 1.3978E-5, 0.7733 + 1.9E-8 , 96.6612 + 3.0565E-5 ,
        19.18171 - 1.55E-8, 0.047318 + 7.45E-9, 142.5905 + 0.011725806
    )
}

//Orbital elements of Neptune:
//
//N = 131.7806 + 3.0173E-5 * d
//i = 1.7700 - 2.55E-7 * d
//w = 272.8461 - 6.027E-6 * d
//a = 30.05826 + 3.313E-8 * d  (AU)
//e = 0.008606 + 2.15E-9 * d
//M = 260.2471 + 0.005995147 * d

fun getNeptuneLocation(time: Date?): RaDec {
    return getLocationViaOrbitalElements(
        time, 131.7806 + 3.0173E-5, 1.7700 - 2.55E-7 , 272.8461 - 6.027E-6,
        30.05826 + 3.313E-8, 0.008606 + 2.15E-9, 260.2471 + 0.005995147
    )
}
