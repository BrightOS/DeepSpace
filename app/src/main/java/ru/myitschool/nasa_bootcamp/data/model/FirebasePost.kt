package ru.myitschool.nasa_bootcamp.data.model.models

import ru.myitschool.nasa_bootcamp.data.model.Comment

data class FirebasePost (
    val postId: Int,
    val comments: List<Comment>,
    val likes: List<String>,  // Лист с пользовательскими ID
)