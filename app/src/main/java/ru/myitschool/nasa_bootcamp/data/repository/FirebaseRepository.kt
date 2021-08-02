package ru.myitschool.nasa_bootcamp.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.utils.Data

interface FirebaseRepository {
    suspend fun getAllPosts(): LiveData<Data<out ArrayList<Post>>>
    suspend fun loadImage(postId: String, imageId: String): LiveData<Data<out Bitmap>>
    suspend fun getAdditionalData(postId: String): LiveData<Data<out ArrayList<PostView>>>
}