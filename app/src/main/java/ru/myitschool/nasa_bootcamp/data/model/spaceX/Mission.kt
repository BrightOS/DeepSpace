package ru.myitschool.nasa_bootcamp.data.model.spaceX

import com.google.gson.annotations.SerializedName

class Mission(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("flight") val flight: String,
)