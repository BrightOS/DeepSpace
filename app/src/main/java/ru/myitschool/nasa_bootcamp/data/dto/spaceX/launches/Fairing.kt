package ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches

import com.google.gson.annotations.SerializedName

class Fairing (
    @field:SerializedName("reused") val reused: Boolean,
    @field:SerializedName("recovery_attempt") val recovery_attempt: Boolean,
    @field:SerializedName("recovered") val recovered: Boolean,
    //@field:SerializedName("ship") val ship: String,
    )