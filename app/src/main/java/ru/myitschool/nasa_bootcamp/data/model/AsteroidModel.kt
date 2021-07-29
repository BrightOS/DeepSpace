package ru.myitschool.nasa_bootcamp.data.model


data class AsteroidModel(
    val id: Long,
    val name: String,
    val absolute_magnitude : Double,
    val estimatedDiameter : Double,
    val kmPerHour : Double,
    val distanceFromEarth: Double,
    val potencialDanger : Boolean
)