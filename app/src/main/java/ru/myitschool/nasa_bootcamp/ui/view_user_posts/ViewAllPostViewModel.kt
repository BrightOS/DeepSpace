package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.utils.Data

interface ViewAllPostViewModel {
    suspend fun getAllPosts(): LiveData<Data<out ArrayList<Post>>>
    suspend fun loadImage(postId: String, imageId: Int)
}