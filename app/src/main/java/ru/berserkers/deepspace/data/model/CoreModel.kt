package ru.berserkers.deepspace.data.model

import ru.berserkers.deepspace.data.dto.spaceX.Mission

/*
 * @author Yana Glad
 */
data class CoreModel(
    val core_serial: String,
    val block: Int?,
    val status: String?,
    val original_launch: String,
    val original_launch_unix: Long,
    val missions: List<Mission>,
    val reuse_count: Int,
    val water_landing: Boolean,
    val details: String?
)
