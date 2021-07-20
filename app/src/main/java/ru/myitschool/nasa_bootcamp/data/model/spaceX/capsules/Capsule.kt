package ru.myitschool.nasa_bootcamp.data.model.spaceX.capsules

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.models.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.spaceX.Mission

class Capsule(
    @field:SerializedName("capsule_serial") val capsule_serial: String,
    @field:SerializedName("capsule_id") val capsule_id: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("original_launch") val original_launch: String,
    @field:SerializedName("original_launch_unix") val original_launch_unix: String,
    @field:SerializedName("missions") val missions: List<Mission>,
    @field:SerializedName("details") val details: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("landings") val landings: Int
) {
    fun createCapsuleModel(): CapsuleModel {
        return CapsuleModel(
            capsule_serial,
            capsule_id,
            status,
            original_launch,
            original_launch_unix,
            missions,
            details,
            type,
            landings
        )
    }
}