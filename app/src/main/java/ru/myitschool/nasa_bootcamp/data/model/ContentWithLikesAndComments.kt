package ru.myitschool.nasa_bootcamp.data.model

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.utils.Resource

data class ContentWithLikesAndComments<T>(
    val id: Long,
    val content: T,
    val likes: MutableLiveData<Resource<List<UserModel>>>,
    val comments: MutableLiveData<Resource<List<MutableLiveData<Comment>>>>
)