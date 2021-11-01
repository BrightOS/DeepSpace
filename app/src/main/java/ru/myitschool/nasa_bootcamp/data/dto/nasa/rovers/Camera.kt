package ru.myitschool.nasa_bootcamp.data.dto.nasa.rovers

import com.google.gson.annotations.SerializedName

/*
 * @author Yana Glad
 */
class Camera( //Название и полное название камеры
      @field:SerializedName("name") val name: String,
      @field:SerializedName("fullname") val fullname: String?
)
