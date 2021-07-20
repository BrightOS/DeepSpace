package ru.myitschool.nasa_bootcamp.data.model.spaceX.dragons

import com.google.gson.annotations.SerializedName

class PayloadMass(
    @field:SerializedName("kg") val kg: Int,
    @field:SerializedName("lb") val lb: Int
 )