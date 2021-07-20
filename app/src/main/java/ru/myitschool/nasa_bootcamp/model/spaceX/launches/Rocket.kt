package ru.myitschool.nasa_bootcamp.model.spaceX.launches

import com.google.gson.annotations.SerializedName

data class Rocket(
    @field:SerializedName("rocket_name") val rocket_name: String,
    @field:SerializedName("rocket_type") val rocket_type: String,
  //  @field:SerializedName("first_stage") val first_stage: FirstStage,
    @field:SerializedName("fairings") val fairings: Fairing,
    )