package ru.myitschool.deepspace.data.model

import ru.myitschool.deepspace.data.dto.spaceX.Mission

/*
 * @author Yana Glad
 */
data class CapsuleModel(
    val capsule_serial: String,
    val capsule_id: String,
    val status: String,
    val original_launch: String?,
    val original_launch_unix: Long,
    val missions: List<Mission>,
    val details: String?,
    val type: String,
    val landings: Int
)
