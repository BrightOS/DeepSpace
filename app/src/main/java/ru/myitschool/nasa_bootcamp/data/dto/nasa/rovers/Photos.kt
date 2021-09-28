package com.example.firstkotlinapp.model.rovers

import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import com.google.gson.annotations.SerializedName

data class Photos(
    @field:SerializedName("id") private val id: Int,
    @field:SerializedName("camera")private val camera: Camera,
    @field:SerializedName("rover") private val rover : Rover,
    @field:SerializedName("img_src") private val img_src: String,
    @field:SerializedName("earth_date") private val earth_date: String

) {
    fun createRoverModel(): RoverModel {
        return RoverModel(id, img_src ,camera, rover, earth_date)
    }
}
