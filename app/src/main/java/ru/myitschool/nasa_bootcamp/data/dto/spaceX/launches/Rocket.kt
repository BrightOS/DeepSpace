package ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Rocket(
    @field:SerializedName("rocket_name") val rocket_name: String,
    @field:SerializedName("rocket_type") val rocket_type: String,
    @field:SerializedName("first_stage") @Embedded val first_stage: FirstStage,
    @field:SerializedName("second_stage")  @Embedded val second_stage: SecondStage,
    @field:SerializedName("fairings") val fairings: Fairing,
    )