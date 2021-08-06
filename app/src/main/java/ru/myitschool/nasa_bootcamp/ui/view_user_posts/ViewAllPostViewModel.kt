package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.utils.Data

interface ViewAllPostViewModel {
    suspend fun getAllPosts(): Data<out ArrayList<Post>>
    suspend fun downloadImage(postId: String, imageId: String): LiveData<Data<out Bitmap>>
    suspend fun getAdditionalData(postId: String): LiveData<Data<out ArrayList<PostView>>>
    fun getViewModelScope(): CoroutineScope
}