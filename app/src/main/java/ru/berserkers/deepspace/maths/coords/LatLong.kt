package ru.berserkers.deepspace.maths.coords

import ru.berserkers.deepspace.maths.matrix.cosineSimilarity
import kotlin.math.PI
import kotlin.math.acos

class LatLong(var latitude: Double, var longitude: Double) {

    fun distanceFrom(other: LatLong): Double {
        val similarity = cosineSimilarity(
            GeocentricCoordinates.getInstance(longitude, latitude),
            GeocentricCoordinates.getInstance(other.longitude, other.latitude
            ))
        return acos(similarity) * 180.0 / PI
    }

    init {
        val longtitudePlus = longitude + 180.0
        val newLong = (if (longtitudePlus < 0) longtitudePlus % 360.0 + 360.0 else longtitudePlus) % 360.0
        longitude = newLong - 180.0
    }
}
