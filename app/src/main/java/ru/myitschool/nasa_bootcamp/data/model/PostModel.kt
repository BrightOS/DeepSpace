package ru.myitschool.nasa_bootcamp.data.model

data class PostModel(
    val id: Long,
    val title: String,
    val date: Long,
    val imageUrl: String?,
    val text: String,
    val author: UserModel
)
