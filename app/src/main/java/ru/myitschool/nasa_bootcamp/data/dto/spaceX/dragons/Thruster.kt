package ru.myitschool.nasa_bootcamp.data.dto.spaceX.dragons

import com.google.gson.annotations.SerializedName

class Thruster(
    @field:SerializedName("type") val type: String,
    @field:SerializedName("amount") val amount: String,
    @field:SerializedName("fuel_1") val fuel_1: String,
    @field:SerializedName("fuel_2") val fuel_2: String,
    @field:SerializedName("pods") val pods: Int,
)
