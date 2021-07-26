package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.CloaseApproachData
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Diametr
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Today

data class AsteroidModel(
    val id: Int,
    val name: String,
    val estimated_diameter: Diametr,
    val dangerous: Boolean,
    val close_approach_data: ArrayList<CloaseApproachData>
)