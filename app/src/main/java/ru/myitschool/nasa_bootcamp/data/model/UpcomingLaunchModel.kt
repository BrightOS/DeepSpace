package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.upcoming.PatchUpcoming

class UpcomingLaunchModel(
    val name: String?,
    val patch: PatchUpcoming?,
    val static_fire_date_unix: Int?,
    val upcoming: Boolean?
)