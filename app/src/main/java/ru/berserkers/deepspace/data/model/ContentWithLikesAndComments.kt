package ru.berserkers.deepspace.data.model


data class ContentWithLikesAndComments<T>(
    val content: T,
    val likes: List<UserModel>,
    val comments: List<Comment>
)
