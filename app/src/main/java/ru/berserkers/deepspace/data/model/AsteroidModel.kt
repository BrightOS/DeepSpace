package ru.berserkers.deepspace.data.model

/*
 * @author Yana Glad
 */
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
