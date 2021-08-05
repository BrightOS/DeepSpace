package ru.myitschool.nasa_bootcamp.data.model

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.utils.Resource

data class ContentWithLikesAndComments<T>(
    val id: Long,
    val content: T,
    val likes: List<UserModel>,
    val comments: List<Comment>
)