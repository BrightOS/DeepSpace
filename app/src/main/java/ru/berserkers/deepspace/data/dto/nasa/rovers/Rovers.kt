package ru.berserkers.deepspace.data.dto.nasa.rovers

import com.google.gson.annotations.SerializedName
import java.util.*

/*
 * @author Yana Glad
 */
data class Rovers (
    @field:SerializedName("photos")
    val photos: ArrayList<Photos>
)
