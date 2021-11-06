package ru.myitschool.deepspace.navigator.rendertype

import android.content.res.Resources
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.maths.astronomy.OrbitalElements
import ru.myitschool.deepspace.maths.astronomy.RaDec
import ru.myitschool.deepspace.maths.astronomy.TimeMachine
import ru.myitschool.deepspace.maths.coords.GeocentricCoordinates
import ru.myitschool.deepspace.maths.coords.HeliocentricCoordinates
import ru.myitschool.deepspace.navigator.maths.DEGREES_TO_RADIANS
import ru.myitschool.deepspace.navigator.maths.RADIANS_TO_DEGREES
import ru.myitschool.deepspace.navigator.maths.mod
import ru.myitschool.deepspace.navigator.pointing.AbstractPointing
import java.util.*
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

enum class Planet(internal val id: Int) {

    Pluto(R.drawable.pluto),
    Neptune(R.drawable.neptune),
    Uranus(R.drawable.uranus),
    Saturn(R.drawable.saturn),
    Jupiter(R.drawable.jupiter),
    Mars(R.drawable.mars),
    Sun(R.drawable.sun),
    Mercury(R.drawable.mercury),
    Venus(R.drawable.venus),
    Moon(R.drawable.moon),
    ISS(R.drawable.isss);

    fun getOrbitalElements(date: Date?): OrbitalElements {
        val time = TimeMachine.julianCenturies(date)
        return when (this) {
            Mercury -> {
                OrbitalElements(
                    eccentricity = 0.38709927 + 0.00000037 * time,
                    inclination = 0.20563593 + 0.00001906 * time,
                    ascnode = (7.00497902 - 0.00594749 * time) * DEGREES_TO_RADIANS,
                    longitude = (48.33076593 - 0.12534081 * time) * DEGREES_TO_RADIANS,
                    perihelion = (77.45779628 + 0.16047689 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((252.25032350 + 149472.67411175 * time) * DEGREES_TO_RADIANS))
            }
            Venus -> {
                OrbitalElements(
                    eccentricity = 0.72333566 + 0.00000390 * time,
                    inclination = 0.00677672 - 0.00004107 * time,
                    ascnode = (3.39467605 - 0.00078890 * time) * DEGREES_TO_RADIANS,
                    longitude = (76.67984255 - 0.27769418 * time) * DEGREES_TO_RADIANS,
                    perihelion = (131.60246718 + 0.00268329 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((181.97909950 + 58517.81538729 * time) * DEGREES_TO_RADIANS))
            }
            Sun -> {
                OrbitalElements(
                    eccentricity = 1.00000261 + 0.00000562 * time,
                    inclination = 0.01671123 - 0.00004392 * time,
                    ascnode = (-0.00001531 - 0.01294668 * time) * DEGREES_TO_RADIANS,
                    longitude = 0.0,
                    perihelion = (102.93768193 + 0.32327364 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((100.46457166 + 35999.37244981 * time) * DEGREES_TO_RADIANS))
            }
            Mars -> {
                OrbitalElements(
                    eccentricity = 1.52371034 + 0.00001847 * time,
                    inclination = 0.09339410 + 0.00007882 * time,
                    ascnode = (1.84969142 - 0.00813131 * time) * DEGREES_TO_RADIANS,
                    longitude = (49.55953891 - 0.29257343 * time) * DEGREES_TO_RADIANS,
                    perihelion = (-23.94362959 + 0.44441088 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((-4.55343205 + 19140.30268499 * time) * DEGREES_TO_RADIANS))
            }
            Jupiter -> {
                OrbitalElements(
                    eccentricity = 5.20288700 - 0.00011607 * time,
                    inclination = 0.04838624 - 0.00013253 * time,
                    ascnode = (1.30439695 - 0.00183714 * time) * DEGREES_TO_RADIANS,
                    longitude = (100.47390909 + 0.20469106 * time) * DEGREES_TO_RADIANS,
                    perihelion = (14.72847983 + 0.21252668 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((34.39644051 + 3034.74612775 * time) * DEGREES_TO_RADIANS))
            }
            Saturn -> {
                OrbitalElements(
                    eccentricity = 9.53667594 - 0.00125060 * time,
                    inclination = 0.05386179 - 0.00050991 * time,
                    ascnode = (2.48599187f + 0.00193609 * time) * DEGREES_TO_RADIANS,
                    longitude = (113.66242448 - 0.28867794 * time) * DEGREES_TO_RADIANS,
                    perihelion = (92.59887831 - 0.41897216 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((49.95424423 + 1222.49362201 * time) * DEGREES_TO_RADIANS))
            }
            Uranus -> {
                OrbitalElements(
                    eccentricity = 19.18916464 - 0.00196176 * time,
                    inclination = 0.04725744 - 0.00004397 * time,
                    ascnode = (0.77263783 - 0.00242939 * time) * DEGREES_TO_RADIANS,
                    longitude = (74.01692503 + 0.04240589 * time) * DEGREES_TO_RADIANS,
                    perihelion = (170.95427630 + 0.40805281 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((313.23810451 + 428.48202785 * time) * DEGREES_TO_RADIANS))
            }
            Neptune -> {
                OrbitalElements(
                    eccentricity = 30.06992276 + 0.00026291 * time,
                    inclination = 0.00859048 + 0.00005105 * time,
                    ascnode = (1.77004347 + 0.00035372 * time) * DEGREES_TO_RADIANS,
                    longitude = (131.78422574 - 0.00508664 * time) * DEGREES_TO_RADIANS,
                    perihelion = (44.96476227 - 0.32241464 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((-55.12002969 + 218.45945325 * time) * DEGREES_TO_RADIANS))
            }
            Pluto -> {
                OrbitalElements(
                    eccentricity = 39.48211675 - 0.00031596 * time,
                    inclination = 0.24882730 + 0.00005170 * time,
                    ascnode = (17.14001206 + 0.00004818 * time) * DEGREES_TO_RADIANS,
                    longitude = (110.30393684 - 0.01183482 * time) * DEGREES_TO_RADIANS,
                    perihelion = (224.06891629 - 0.04062942 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = mod((238.92903833f + 145.20780515 * time) * DEGREES_TO_RADIANS))
            }
            ISS -> {
                OrbitalElements(
                    eccentricity = 0.001220230040456721 + 0.0001925 * time,
                    inclination = 51.58120030298049 + 0.00005170 * time,
                    ascnode = (51.5914 + 0.05818 * time) * DEGREES_TO_RADIANS,
                    longitude = (223.77968914802696 - 0.01183482 * time) * DEGREES_TO_RADIANS,
                    perihelion = (122.33400836496219 * time) * DEGREES_TO_RADIANS,
                    meanLongitude = 0.0,
                    trueAnomaly2 = 237.77599772309975
                )
            }
            else -> throw RuntimeException("Moon should npt be here")
        }
    }

    val scalePlanets: Double
        get() = when (this) {
            Sun -> SUN_SCALE
            Moon -> MOON_SCALE
            Mercury, Venus, Mars, Pluto -> MERCURY_SCALE
            Jupiter -> JUPITER_SCALE
            Uranus, Neptune -> URANUS_SCALE
            Saturn -> JUPITER_SCALE
            ISS -> ISS_SCALE
        }

    class PlanetSource(
        private val planet: Planet, private val resources: Resources,
        private val model: AbstractPointing,
    ) {

        private val icons = ArrayList<ImageRun>()
        private val currentCoords = GeocentricCoordinates(0.0, 0.0, 0.0)
        private lateinit var sunCoords: HeliocentricCoordinates
        private var imageId = -1
        private var lastUpdateTimeMs = 0L

        fun setupIcon(): List<ImageRun> {
            val time = model.time
            updateCoords(time)
            imageId = planet.id

            icons.add(
                ImageRun(
                    coords = currentCoords,
                    resources = resources,
                    id = imageId,
                    upVec = sunCoords,
                    imageScale = planet.scalePlanets
                )
            )
            return icons
        }

        private fun updateCoords(time: Date) {
            lastUpdateTimeMs = time.time
            sunCoords = HeliocentricCoordinates.getInstance(Sun, time)

            currentCoords.updateFromRaDec(RaDec.getInstance(
                planet = planet,
                time = time,
                earthCoordinates = sunCoords
            ))

            for (icon in icons)
                icon.setUpVector(sunCoords)
        }
    }

    companion object {
        const val SUN_SCALE = 0.08
        const val MOON_SCALE = 0.07
        const val MERCURY_SCALE = 0.03
        const val JUPITER_SCALE = 0.04
        const val URANUS_SCALE = 0.02
        const val ISS_SCALE = 0.03

        fun Date.getMoonPosition(): RaDec {
            val time = ((TimeMachine.julianDay(this) - 2451545.0) / 36525.0)

            val lambda = (218.32f + 481267.881 * time + (6.29
                    * sin((135.0 + 477198.87 * time) * DEGREES_TO_RADIANS)) - 1.27
                    * sin((259.3 - 413335.36 * time) * DEGREES_TO_RADIANS)) + (0.66
                    * sin((235.7 + 890534.22 * time) * DEGREES_TO_RADIANS)) + (0.21
                    * sin((269.9 + 954397.74 * time) * DEGREES_TO_RADIANS)) - (0.19
                    * sin((357.5 + 35999.05 * time) * DEGREES_TO_RADIANS)) - (0.11
                    * sin((186.5 + 966404.03 * time) * DEGREES_TO_RADIANS))
            val beta = (5.13f * sin((93.3 + 483202.02 * time) * DEGREES_TO_RADIANS) + 0.28
                    * sin((228.2 + 960400.89 * time) * DEGREES_TO_RADIANS)) - (0.28
                    * sin((318.3 + 6003.15 * time) * DEGREES_TO_RADIANS)) - (0.17
                    * sin((217.6 - 407332.21 * time) * DEGREES_TO_RADIANS))

            val l = (cos(beta * DEGREES_TO_RADIANS)
                    * cos(lambda * DEGREES_TO_RADIANS))
            val m = (0.9175f * cos(beta * DEGREES_TO_RADIANS)
                    * sin(lambda * DEGREES_TO_RADIANS)) - 0.3978f * sin(beta * DEGREES_TO_RADIANS)
            val n = (0.3978f * cos(beta * DEGREES_TO_RADIANS)
                    * sin(lambda * DEGREES_TO_RADIANS)) + 0.9175f * sin(beta * DEGREES_TO_RADIANS)
            val ra: Double = mod(atan2(m, l)) * RADIANS_TO_DEGREES
            val dec: Double = asin(n) * RADIANS_TO_DEGREES

            return RaDec(ra, dec)
        }
    }
}