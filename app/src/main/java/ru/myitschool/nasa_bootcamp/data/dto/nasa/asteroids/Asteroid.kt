package ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel

class Asteroid(
    @field:SerializedName("element_count") val element_count: Int,
    @field:SerializedName("near_earth_objects") val near_earth_objects: Today
){


}