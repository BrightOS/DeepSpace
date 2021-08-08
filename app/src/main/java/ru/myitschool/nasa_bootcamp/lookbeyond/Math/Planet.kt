package ru.myitschool.nasa_bootcamp.lookbeyond.Math


import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.TimeMachine.julianCenturies
import ru.myitschool.nasa_bootcamp.utils.DEGREES_TO_RADIANS
import ru.myitschool.nasa_bootcamp.utils.RADIANS_TO_DEGREES
import java.util.*
import kotlin.math.*


enum class Planet(
    private val imageResourceId: Int
) {
    Pluto(R.drawable.pluto),
    Neptune(R.drawable.neptune),
    Jupiter(R.drawable.jupiter),
    Uranus(R.drawable.uranus),
    Saturn(R.drawable.saturn),
    Mercury(R.drawable.mercury),
    Venus(R.drawable.venus),
    Mars(R.drawable.mars),
    Sun(R.drawable.sun),
    Moon(R.drawable.moon),
    ISS(R.drawable.isss);

    fun getImageResourceId(): Int {
        return imageResourceId
    }


    fun getOrbitalElements(date: Date?): OrbitalElements {
        val time = julianCenturies(date)
        return when (this) {
            Mercury -> {
                OrbitalElements(
                    0.38709927 + 0.00000037 * time,
                    0.20563593 + 0.00001906 * time,
                    (7.00497902 - 0.00594749 * time) * DEGREES_TO_RADIANS,
                    (48.33076593 - 0.12534081 * time) * DEGREES_TO_RADIANS,
                    (77.45779628 + 0.16047689 * time) * DEGREES_TO_RADIANS,
                    modPart((252.25032350 + 149472.67411175 * time) * DEGREES_TO_RADIANS)
                )
            }
            Venus -> {
                OrbitalElements(
                    0.72333566 + 0.00000390 * time,
                    0.00677672 - 0.00004107 * time,
                    (3.39467605 - 0.00078890 * time) * DEGREES_TO_RADIANS,
                    (76.67984255 - 0.27769418 * time) * DEGREES_TO_RADIANS,
                    (131.60246718 + 0.00268329 * time) * DEGREES_TO_RADIANS,
                    modPart((181.97909950 + 58517.81538729 * time) * DEGREES_TO_RADIANS)
                )
            }
            Sun -> {
                OrbitalElements(
                    1.00000261 + 0.00000562 * time,
                    0.01671123 - 0.00004392 * time,
                    (-0.00001531 - 0.01294668 * time) * DEGREES_TO_RADIANS,
                    0.0,
                    (102.93768193 + 0.32327364 * time) * DEGREES_TO_RADIANS,
                    modPart((100.46457166 + 35999.37244981 * time) * DEGREES_TO_RADIANS)
                )
            }
            Mars -> {
                OrbitalElements(
                    1.52371034 + 0.00001847 * time,
                    0.09339410 + 0.00007882 * time,
                    (1.84969142 - 0.00813131 * time) * DEGREES_TO_RADIANS,
                    (49.55953891 - 0.29257343 * time) * DEGREES_TO_RADIANS,
                    (-23.94362959 + 0.44441088 * time) * DEGREES_TO_RADIANS,
                    modPart((-4.55343205 + 19140.30268499 * time) * DEGREES_TO_RADIANS)
                )
            }
            Jupiter -> {
                OrbitalElements(
                    5.20288700 - 0.00011607 * time,
                    0.04838624 - 0.00013253 * time,
                    (1.30439695 - 0.00183714 * time) * DEGREES_TO_RADIANS,
                    (100.47390909 + 0.20469106 * time) * DEGREES_TO_RADIANS,
                    (14.72847983 + 0.21252668 * time) * DEGREES_TO_RADIANS,
                    modPart((34.39644051 + 3034.74612775 * time) * DEGREES_TO_RADIANS)
                )
            }
            Saturn -> {
                OrbitalElements(
                    9.53667594 - 0.00125060 * time,
                    0.05386179 - 0.00050991 * time,
                    (2.48599187 + 0.00193609 * time) * DEGREES_TO_RADIANS,
                    (113.66242448 - 0.28867794 * time) * DEGREES_TO_RADIANS,
                    (92.59887831 - 0.41897216 * time) * DEGREES_TO_RADIANS,
                    modPart((49.95424423 + 1222.49362201 * time) * DEGREES_TO_RADIANS)
                )
            }
            Uranus -> {

                OrbitalElements(
                    19.18916464 - 0.00196176 * time,
                    0.04725744 - 0.00004397 * time,
                    (0.77263783 - 0.00242939 * time) * DEGREES_TO_RADIANS,
                    (74.01692503 + 0.04240589 * time) * DEGREES_TO_RADIANS,
                    (170.95427630 + 0.40805281 * time) * DEGREES_TO_RADIANS,
                    modPart((313.23810451 + 428.48202785 * time) * DEGREES_TO_RADIANS)
                )
            }
            Neptune -> {
                OrbitalElements(
                    30.06992276 + 0.00026291 * time,
                    0.00859048 + 0.00005105 * time,
                    (1.77004347 + 0.00035372 * time) * DEGREES_TO_RADIANS,
                    (131.78422574 - 0.00508664 * time) * DEGREES_TO_RADIANS,
                    (44.96476227 - 0.32241464 * time) * DEGREES_TO_RADIANS,
                    modPart((-55.12002969 + 218.45945325 * time) * DEGREES_TO_RADIANS)
                )
            }
            Pluto -> {
                OrbitalElements(
                    39.48211675 - 0.00031596 * time,
                    0.24882730 + 0.00005170 * time,
                    (17.14001206 + 0.00004818 * time) * DEGREES_TO_RADIANS,
                    (110.30393684 - 0.01183482 * time) * DEGREES_TO_RADIANS,
                    (224.06891629 - 0.04062942 * time) * DEGREES_TO_RADIANS,
                    modPart((238.92903833 + 145.20780515 * time) * DEGREES_TO_RADIANS)
                )
            }
            ISS -> {
                OrbitalElements(
                    0.001220230040456721 +  0.0001925 * time,
                    51.58120030298049 + 0.00005170 * time,
                    (51.5914 + 0.05818 * time) * DEGREES_TO_RADIANS,
                    (223.77968914802696 - 0.01183482 * time) * DEGREES_TO_RADIANS,
                    (122.33400836496219 * time) * DEGREES_TO_RADIANS,
                    0.0,
                    237.77599772309975
                )
            }
            else -> throw UNKNOWN_PLANET("Planet.kt", "No such Planet $this")
        }
    }

    val planetaryImageSize: Double
        get() = when (this) {
            Sun, Moon -> 0.07
            Mercury, Venus, Mars, Pluto -> 0.03
            Jupiter -> 0.04
            Uranus, Neptune -> 0.02
            Saturn -> 0.04
            ISS -> 0.03
        }


    companion object {


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
                time, 76.6799 + 2.46590E-5, 3.3946 + 2.75E-8, 54.8910 + 1.38374E-5,
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
                time, 49.5574 + 2.11081E-5, 1.8497 - 1.78E-8, 286.5016 + 2.92961E-5,
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
                time, 100.4542 + 2.76854E-5, 1.3030 - 1.557E-7, 273.8777 + 1.64505E-5,
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
                time, 113.6634 + 2.38980E-5, 2.4886 - 1.081E-7, 339.3939 + 2.97661E-5,
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
                time, 74.0005 + 1.3978E-5, 0.7733 + 1.9E-8, 96.6612 + 3.0565E-5,
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
                time, 131.7806 + 3.0173E-5, 1.7700 - 2.55E-7, 272.8461 - 6.027E-6,
                30.05826 + 3.313E-8, 0.008606 + 2.15E-9, 260.2471 + 0.005995147
            )
        }

        fun getMoonLocation(time: Date?): RaDec {
            return getLocationViaOrbitalElements(
                time, 125.1228 - 0.0529538083, 5.1454, 318.0634 + 0.1643573223,
                60.2666, 0.054900, 115.3654 + 13.0649929509
            )
        }

        //
        fun getLocationViaOrbitalElements(
            time: Date?, _longtitude: Double,
            _inclination: Double, _perihelion: Double,
            _axis: Double, _eccentricity: Double, _meanAnomaly: Double
        ): RaDec {
            val t = julianCenturies(time).toDouble()

//    Orbital elements of the Moon:
//    N = longitude of the ascending node
//    i = inclination to the ecliptic (plane of the Earth's orbit)
//    w = argument of perihelion
//    a = semi-major axis, or mean distance from Sun
//    e = eccentricity (0=circle, 0-1=ellipse, 1=parabola)
//    M = mean anomaly (0 at perihelion; increases uniformly with time)

            val longtitude = _longtitude * t
            val inclination = _inclination
            val perihelion = _perihelion * t
            val axis = _axis * t
            val meanAnomaly = _meanAnomaly * t


            val E =
                meanAnomaly + _eccentricity * (180 / Math.PI) * sin(meanAnomaly) * (1.0 + _eccentricity * Math.cos(
                    meanAnomaly
                ))
            //or  E = M + e * sin(M) * ( 1.0 + e * cos(M) ) in radians

            val xv = cos(E) - _eccentricity
            val yv = sqrt(1.0 - _eccentricity * _eccentricity) * Math.sin(E)

            val v = atan2(yv, xv)
            val r = sqrt(xv * xv + yv * yv)

            val lonsun = v + perihelion

            val xs = r * cos(lonsun)
            val ys = r * sin(lonsun)

            val ecl = 23.4393 - 3.563E-7 * t

            val ye = ys * cos(ecl)
            val ze = ys * sin(ecl)

            val ra = atan2(ye, xs)
            val dec = atan2(ze, Math.sqrt(xs * xs + ye * ye))

            return RaDec(
                ra,
                dec,
                longtitude,
                inclination,
                perihelion,
                axis,
                _eccentricity,
                meanAnomaly
            )
        }

        //NASA
        fun getMoonLocation1(time: Date?): RaDec {
            val t = ((TimeMachine.julianDay(time) - 2451545.0) / 36525.0)

            val lambda =
                (218.32 + 481267.881 * t + (6.29 * sin((135.0 + 477198.87 * t) * DEGREES_TO_RADIANS)) - 1.27 * sin(
                    (259.3 - 413335.36 * t) * DEGREES_TO_RADIANS
                )) + (0.66 * sin((235.7 + 890534.22 * t) * DEGREES_TO_RADIANS)) + (0.21 * sin(
                    (269.9 + 954397.74 * t) * DEGREES_TO_RADIANS
                )) - (0.19 * sin(
                    (357.5 + 35999.05 * t)
                            * DEGREES_TO_RADIANS
                )) - (0.11 * sin((186.5 + 966404.03 * t) * DEGREES_TO_RADIANS))

            val beta = (5.13 * sin((93.3 + 483202.02 * t) * DEGREES_TO_RADIANS) + 0.28
                    * sin((228.2 + 960400.89 * t) * DEGREES_TO_RADIANS)) - (0.28 * sin((318.3 + 6003.15 * t) * DEGREES_TO_RADIANS)) - (0.17 * sin(
                (217.6 - 407332.21 * t) * DEGREES_TO_RADIANS
            ))

            val l = (cos(beta * DEGREES_TO_RADIANS) * cos(lambda * DEGREES_TO_RADIANS))

            val m =
                (0.9175 * cos(beta * DEGREES_TO_RADIANS) * sin(lambda * DEGREES_TO_RADIANS)) - 0.3978 * sin(
                    beta * DEGREES_TO_RADIANS
                )

            val n =
                (0.3978 * cos(beta * DEGREES_TO_RADIANS) * sin(lambda * DEGREES_TO_RADIANS)) + 0.9175 * sin(
                    beta * DEGREES_TO_RADIANS
                )

            val ra: Double = modPart(atan2(m, l)) * RADIANS_TO_DEGREES
            val dec: Double = asin(n) * RADIANS_TO_DEGREES
            return RaDec(ra, dec)
        }
    }
}