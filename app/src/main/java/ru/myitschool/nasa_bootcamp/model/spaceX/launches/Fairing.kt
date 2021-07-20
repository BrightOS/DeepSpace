package ru.myitschool.nasa_bootcamp.model.spaceX.launches

import com.google.gson.annotations.SerializedName

class Fairing (
    @field:SerializedName("reused") val reused: Boolean,
    @field:SerializedName("recovery_attempt") val recovery_attempt: String,
    @field:SerializedName("recovered") val recovered: String,
    //@field:SerializedName("ship") val ship: String,
    )