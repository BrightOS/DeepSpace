package ru.myitschool.nasa_bootcamp.lookbeyond.maths

class LatLong(var latitude: Double, var longitude: Double)  {
    companion object {
        private fun flooredMod(a: Double, n: Double): Double {
            return (if (a < 0) a % n + n else a) % n
        }
    }
    init {
        longitude = flooredMod(longitude + 180.0, 360.0) - 180.0
    }
}
