package ru.myitschool.nasa_bootcamp.data.dto.nasa.rovers

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.RoverModel

/*
 * @author Yana Glad
 */
data class Photos(
    @field:SerializedName("id") private val id: Int,
    @field:SerializedName("camera")private val camera: Camera,
    @field:SerializedName("rover") private val rover : Rover,
    @field:SerializedName("img_src") private val img_src: String,
    @field:SerializedName("earth_date") private val earth_date: String

) {
    fun createRoverModel(): RoverModel {
        return RoverModel(id, img_src ,camera, rover, earth_date)
    }
}
