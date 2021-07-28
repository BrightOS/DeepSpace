package ru.myitschool.nasa_bootcamp.data.model

import com.google.gson.annotations.SerializedName

data class ArticleModel(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String
)