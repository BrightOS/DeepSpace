package ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/*
 * @author Yana Glad
 */
class Asteroid(
    @field:SerializedName("element_count") val element_count: Int,
    @field:SerializedName("near_earth_objects") val near_earth_objects: JsonObject
){


}