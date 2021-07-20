package com.example.firstkotlinapp.model.rovers

import com.google.gson.annotations.SerializedName
import java.util.*

data class Rovers (
    @field:SerializedName("photos")
    val photos: ArrayList<Photos>

)

