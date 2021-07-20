package ru.myitschool.nasa_bootcamp.model.models

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.model.spaceX.Mission

data class CapsuleModel(
    val capsule_serial: String,
    val capsule_id: String,
    val status: String,
    val original_launch: String,
    val original_launch_unix: String,
    val missions: List<Mission>,
    val details: String,
    val type: String,
    val landings: Int
)
