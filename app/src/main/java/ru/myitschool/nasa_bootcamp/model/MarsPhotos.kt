package com.example.firstkotlinapp.model

import com.google.gson.annotations.SerializedName

data class MarsPhotos(
    @field:SerializedName("id") private val id: Int,
    @field:SerializedName("sol") private val sol: Int,
    @field:SerializedName("img_src") private val img_src: String,
    @field:SerializedName("earth_date") private val earth_date: String
)
