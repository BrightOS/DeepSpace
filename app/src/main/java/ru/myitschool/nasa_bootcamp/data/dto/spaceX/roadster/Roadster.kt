package ru.myitschool.nasa_bootcamp.data.dto.spaceX.roadster

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.RoadsterModel

class Roadster(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("launch_date_utc") val launch_date_utc: String,
    @field:SerializedName("launch_date_unix") val launch_date_unix: Long,
    @field:SerializedName("launch_mass_kg") val launch_mass_kg: Int,
    @field:SerializedName("orbit_type") val orbit_type: String,
    @field:SerializedName("longitude") val longitude: Double,
    @field:SerializedName("earth_distance_km") val earth_distance_km: Double,
    @field:SerializedName("mars_distance_km") val mars_distance_km: Double,
    @field:SerializedName("details") val details: String,
) {

    public fun createRoadsterModel(): RoadsterModel {
        return RoadsterModel(
            name,
            launch_date_utc,
            launch_date_unix,
            launch_mass_kg,
            orbit_type,
            longitude,
            earth_distance_km,
            mars_distance_km,
            details
        )
    }
}