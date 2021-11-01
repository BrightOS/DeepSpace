package ru.myitschool.nasa_bootcamp.data.model

import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.LaunchSite
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Links
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Rocket

/*
 * @author Yana Glad
 */
data class SxLaunchModel(
    val launch_date_unix: Long,
    val flight_number: Int,
    val mission_name: String,
    val launch_year: Int,
    val launch_date_utc: String,
    val links: Links?, //Cсылка на эмблему миссии
    val rocket: Rocket,
    val upcoming: Boolean,
    val launch_success: Boolean,
    val details: String?,
    val launch_site: LaunchSite)