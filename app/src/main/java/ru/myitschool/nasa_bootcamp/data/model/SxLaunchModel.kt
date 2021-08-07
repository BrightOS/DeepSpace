package ru.myitschool.nasa_bootcamp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.*

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