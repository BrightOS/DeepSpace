package ru.myitschool.nasa_bootcamp.data.model


data class AsteroidModel(
    val id: Long,
    val name: String,
    val date: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter : String,
    val speed: Double,
    val distanceFromEarth: Double,
    val potencialDanger : Boolean
)