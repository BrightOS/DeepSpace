package ru.myitschool.nasa_bootcamp.data.model

import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landPads.LocationPad

/*
 * @author Yana Glad
 */
class LandPadModel(
    val id: String,
    val full_name: String,
    val status: String,
    val location: LocationPad,
    val landing_type: String,
    val attempted_landings: Int,
    val successful_landings: Int,
    val wikipedia: String,
    val details: String
)