package ru.myitschool.nasa_bootcamp.model.spaceX.cores

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.model.models.CoreModel
import ru.myitschool.nasa_bootcamp.model.spaceX.Mission
import ru.myitschool.nasa_bootcamp.model.spaceX.capsules.Capsule

class Core(
    @field:SerializedName("core_serial") val core_serial: String,
    @field:SerializedName("block") val block: Int?,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("original_launch") val original_launch: String,
    @field:SerializedName("original_launch_unix") val original_launch_unix: Int,
    @field:SerializedName("missions") val missions: List<Mission>,
    @field:SerializedName("reuse_count") val reuse_count: Int,
    @field:SerializedName("water_landing") val water_landing: Boolean,
    @field:SerializedName("details") val details: String
) {

    fun createCoreModel(): CoreModel {
        return CoreModel(
            core_serial,
            block,
            status,
            original_launch,
            original_launch_unix,
            missions,
            reuse_count,
            water_landing,
            details
        )
    }
}