package ru.myitschool.deepspace.maths.astronomy

import ru.myitschool.deepspace.maths.coords.HeliocentricCoordinates
import ru.myitschool.deepspace.maths.coords.LatLong
import ru.myitschool.deepspace.navigator.maths.RADIANS_TO_DEGREES
import ru.myitschool.deepspace.navigator.maths.mod
import ru.myitschool.deepspace.navigator.rendertype.Planet
import ru.myitschool.deepspace.navigator.rendertype.Planet.Companion.getMoonPosition
import java.util.*
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

fun getZenith(utc: Date?, location: LatLong): RaDec {
    val ra = TimeMachine.meanSiderealTime(utc, location.longitude)
    val dec = location.latitude
    return RaDec(ra, dec)
}

class RaDec(
    var ra: Double,
    var dec: Double,
) {

    companion object {
        private fun getDistanceRadec(coords: HeliocentricCoordinates): RaDec {
            //   RA и DEC из экваториальных
            val ra = mod(atan2(coords.y, coords.x)) * RADIANS_TO_DEGREES
            val dec = (atan(coords.z / sqrt(coords.x * coords.x + coords.y * coords.y)) * RADIANS_TO_DEGREES)
            return RaDec(ra, dec)
        }

        fun getInstance(
            planet: Planet,
            time: Date,
            earthCoordinates: HeliocentricCoordinates,
        ): RaDec {
            if (planet == Planet.Moon) {
                return time.getMoonPosition()
            }
            var coords: HeliocentricCoordinates?
            if (planet == Planet.Sun) {
                //Если солнце, нужно отразить координаты т.к. нужно солнце в ЗЕМНЫХ координатах, а не земля в солнечных
                coords = HeliocentricCoordinates(
                    earthCoordinates.radius, earthCoordinates.x * -1.0,
                    earthCoordinates.y * -1.0, earthCoordinates.z * -1.0
                )
            } else {
                coords = HeliocentricCoordinates.getInstance(planet, time)
                coords-=earthCoordinates
            }
            return getDistanceRadec(coords.heliocentric())
        }
    }
}