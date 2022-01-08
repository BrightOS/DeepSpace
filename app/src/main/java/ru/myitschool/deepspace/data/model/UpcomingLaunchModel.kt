package ru.myitschool.deepspace.data.model

import ru.myitschool.deepspace.data.dto.upcoming.PatchUpcoming

class UpcomingLaunchModel(
    val name: String?,
    val patch: PatchUpcoming?,
    val static_fire_date_unix: Long?,
    val upcoming: Boolean?
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is UpcomingLaunchModel) return false
        return name == other.name && patch == other.patch && static_fire_date_unix == other.static_fire_date_unix && upcoming == other.upcoming
    }
}
