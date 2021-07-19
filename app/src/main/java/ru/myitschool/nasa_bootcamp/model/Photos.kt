package com.example.firstkotlinapp.model

import com.example.firstkotlinapp.model.models.RoverModel
import com.google.gson.annotations.SerializedName

data class Photos(
    @field:SerializedName("id") private val id: Int,
    @field:SerializedName("camera")private val camera: Camera,
    @field:SerializedName("rover") private val rover : Rover,
    @field:SerializedName("img_src") private val img_src: String
) {
    fun createRoverModel(): RoverModel {
        return RoverModel(id, img_src ,camera, rover)
    }
}
