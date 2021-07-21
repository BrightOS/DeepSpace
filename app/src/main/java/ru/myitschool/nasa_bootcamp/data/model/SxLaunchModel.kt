package ru.myitschool.nasa_bootcamp.data.model

import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.LaunchSite
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Links
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Rocket

data class SxLaunchModel(
    val launch_site: LaunchSite,
    val rocket: Rocket,
    val links: Links,
    val flight_number: Int,
    val mission_name: String,
    val launch_year: Int,
    val launch_date_utc: String,
    val upcoming: Boolean,
    val launch_success: Boolean,
    val details: String
)