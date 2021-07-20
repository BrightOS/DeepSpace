package ru.myitschool.nasa_bootcamp.model.spaceX.launches

import com.google.gson.annotations.SerializedName

class LaunchSite(
    @field:SerializedName("site_name_long") val site_name_long: String
)