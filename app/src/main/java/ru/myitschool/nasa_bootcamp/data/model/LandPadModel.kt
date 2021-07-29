package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landPads.LocationPad

class LandPadModel(
    val id: String,
    val full_name: String,
    val status: String,
    val location: LocationPad
)