package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landingPads.LocationPadLaunch

data class LaunchPadModel(
    val id: Int,
    val status: String,
    val name: String,
    val location: LocationPadLaunch
)
