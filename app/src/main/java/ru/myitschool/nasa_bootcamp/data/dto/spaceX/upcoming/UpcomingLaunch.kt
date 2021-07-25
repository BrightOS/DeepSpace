package ru.myitschool.nasa_bootcamp.data.dto.spaceX.upcoming

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Links
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Rocket

class UpcomingLaunch( //MAIN !!! in V5
    @field:SerializedName("webcast") val webcast: String,
    @field:SerializedName("flight_number") val flight_number: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("date_utc") val date_utc: String,
    @field:SerializedName("date_unix") val date_unix: Int,
    @field:SerializedName("links") val links: Links, //Cсылка на эмблему миссии
    @field:SerializedName("rocket") val rocket: Rocket,
    @field:SerializedName("upcoming") val upcoming: Boolean,
    @field:SerializedName("success") val success: Boolean,
    @field:SerializedName("details") val details: String

)