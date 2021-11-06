package ru.myitschool.deepspace.data.dto.spaceX

import com.google.gson.annotations.SerializedName

class Mission(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("flight") val flight: String,
)
