package ru.berserkers.deepspace.data.dto.spaceX.launches

import com.google.gson.annotations.SerializedName

data class Links(
    @field:SerializedName("mission_patch_small")
    val mission_patch_small: String?,
    @field:SerializedName("mission_patch")
    val mission_patch: String?,
    @field:SerializedName("video_link")
    val video_link: String?
)
