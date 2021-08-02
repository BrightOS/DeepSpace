package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.utils.Data

interface CreatePostViewModel {
    suspend fun createPost(post: Post, postId: String) : LiveData<Data<out String>>
    fun uploadImage(postId: String, imageId: Int,  imagePath: Uri) : LiveData<Data<out String>>
    fun getLastPostId():  LiveData<Data<out String>>
    fun getViewModelScope(): CoroutineScope
}