package ru.myitschool.nasa_bootcamp.lookbeyond.Math

import ru.myitschool.nasa_bootcamp.utils.DEGREE_180
import ru.myitschool.nasa_bootcamp.utils.DEGREE_360


class LatLong(var latitude: Double, var longitude: Double) {

    companion object {
        private fun flooredMod(a: Double, n: Double): Double {
            return (if (a < 0) a % n + n else a) % n
        }
    }

    init {
        longitude = flooredMod(longitude + DEGREE_180, DEGREE_360) - DEGREE_180
    }
}