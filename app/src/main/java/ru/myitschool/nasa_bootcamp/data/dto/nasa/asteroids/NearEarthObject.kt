package ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel

data class Today(
    @field:SerializedName("2015-09-07") val date: ArrayList<NearEarthObject>
)
data class NearEarthObject(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("estimated_diameter") val estimated_diameter: Diametr,
    @field:SerializedName("is_potentially_hazardous_asteroid") val dangerous: Boolean,
    @field:SerializedName("close_approach_data") val close_approach_data: ArrayList<CloaseApproachData>
) {

    fun createAsteroidModel() : AsteroidModel {
        return AsteroidModel(id, name, estimated_diameter, dangerous, close_approach_data)
    }
}

data class Diametr(
    @field:SerializedName("kilometers") val kilometers: Kilometers
)

data class Kilometers(
    @field:SerializedName("estimated_diameter_min") val estimated_diameter_min: Double,
    @field:SerializedName("estimated_diameter_max") val estimated_diameter_max: Double
)

data class CloaseApproachData(
    @field:SerializedName("close_approach_date_full") val close_approach_date_full: String,
    @field:SerializedName("relative_velocity") val relative_velocity: RelativeVelocity
)

data class RelativeVelocity(
    @field:SerializedName("kilometers_per_second") val kilometers_per_second: String,
    @field:SerializedName("kilometers_per_hour") val kilometers_per_hour: String,
    @field:SerializedName("miles_per_hour") val miles_per_hour: String,
)
