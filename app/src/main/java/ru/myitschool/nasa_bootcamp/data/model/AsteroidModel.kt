package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.CloaseApproachData
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Diametr
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Today

data class AsteroidModel(
    val id: Long,
    val name: String,
    val absolute_magnitude : Double,
    val estimatedDiameter : Double,
    val relativeVelocity : Double,
    val distanceFromEarth: Double,
    val potncialDanger : Boolean
)