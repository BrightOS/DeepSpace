package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import ru.myitschool.nasa_bootcamp.data.model.Post

interface CreatePostViewModel {
    suspend fun createPost(post: Post)
    suspend fun loadImage(postId: String, imageId: Int,  imagePath: Uri)
    fun getLastPostId(): String
}