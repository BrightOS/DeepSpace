package ru.berserkers.deepspace.data.model


data class PostModel(
    val id: Long,
    val title: String,
    val date: Long,
    val postItems: List<String>,
    val author: UserModel
)
