package ru.berserkers.deepspace.data.model

import ru.berserkers.deepspace.data.dto.spaceX.landingPads.LocationPadLaunch

/*
 * @author Yana Glad
 */
data class LaunchPadModel(
    val id: Int,
    val status: String,
    val name: String,
    val location: LocationPadLaunch,
    val vehicles_launched: ArrayList<String>,
    val attempted_launches: Int,
    val successful_launches: Int,
    val wikipedia: String,
    val details: String,
    val site_name_long: String
)
