package com.example.kotlintraining.model

import com.example.firstkotlinapp.model.models.ApodModel
import com.google.gson.annotations.SerializedName

data class Apod(
    @field:SerializedName("copyright") val copyright: String,
    @field:SerializedName("date") val date: String,
    @field:SerializedName("explanation") val explanation: String,
    @field:SerializedName("hdurl") val hdurl: String,
    @field:SerializedName("media_type") val media_type: String,
    @field:SerializedName("service_version") val service_version: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
) {

    fun createApodModel(): ApodModel {

        return ApodModel(
            copyright,
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