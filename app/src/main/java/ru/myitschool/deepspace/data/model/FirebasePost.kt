package ru.myitschool.deepspace.data.model.models

import ru.myitschool.deepspace.data.model.Comment

data class FirebasePost (
    val postId: Int,
    val comments: List<Comment>,
    val likes: List<String>,  // Лист с пользовательскими ID
)
