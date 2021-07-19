package com.example.firstkotlinapp.model

import com.google.gson.annotations.SerializedName

class Camera( //Название и полное название камеры
      @field:SerializedName("name") val name: String,
      @field:SerializedName("fullname") val fullname: String

)
