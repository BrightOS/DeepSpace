package com.example.firstkotlinapp.model.apod

import com.google.gson.annotations.SerializedName
import ru.myitschool.deepspace.data.model.ImageOfTheDayModel

/*
 * @author Yana Glad
 */
data class Apod(
    @field:SerializedName("date") val date: String,
    @field:SerializedName("explanation") val explanation: String,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("media_type") val media_type: String?,
    @field:SerializedName("service_version") val service_version: String?,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
) {

    fun createApodModel(): ImageOfTheDayModel {

        return ImageOfTheDayModel(
            date,
            explanation,
            hdurl,
            media_type,
            service_version,
            title,
            url
        )
    }
}
