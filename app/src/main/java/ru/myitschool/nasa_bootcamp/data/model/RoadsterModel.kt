package ru.myitschool.nasa_bootcamp.data.model

data class RoadsterModel(
    val name: String,
    val launch_date_utc: String,
    val launch_date_unix: Int,
    val launch_mass_kg: Int,
    val orbit_type: String,
    val longitude: Double,
    val earth_distance_km: Double,
    val mars_distance_km: Double,
    val details: Double,
)