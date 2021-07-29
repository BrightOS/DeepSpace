package ru.myitschool.nasa_bootcamp.data.model

import ru.myitschool.nasa_bootcamp.data.dto.spaceX.Mission

data class CoreModel(
    val core_serial: String,
    val block: Int?,
    val status: String?,
    val original_launch: String,
    val original_launch_unix: Int,
    val missions: List<Mission>,
    val reuse_count: Int,
    val water_landing: Boolean,
    val details: String?
)
