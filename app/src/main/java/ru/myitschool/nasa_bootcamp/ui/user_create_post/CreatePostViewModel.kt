package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.utils.Data

interface CreatePostViewModel {
    suspend fun createPost(post: Post) : LiveData<Data<out String>>
    fun loadImage(postId: String, imageId: Int,  imagePath: Uri) : LiveData<Data<out String>>
    fun getLastPostId(): String
}