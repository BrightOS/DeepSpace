package ru.myitschool.nasa_bootcamp.data.model


data class PostModel(
    val id: Long,
    val title: String,
    val date: Long,
    val postItems: List<String>,
    val author: UserModel
)
