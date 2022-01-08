package ru.myitschool.deepspace.data.model

data class RoadsterModel(
    val name: String,
    val launch_date_utc: String,
    val launch_date_unix: Long,
    val launch_mass_kg: Int,
    val orbit_type: String,
    val longitude: Double,
    val earth_distance_km: Double,
    val mars_distance_km: Double,
    val details: String,
)
