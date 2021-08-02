package ru.myitschool.nasa_bootcamp.data.model

data class ContentWithLikesAndComments<T>(
    val content: T,
    val likes: Int,
    val comments: List<Comment>
)