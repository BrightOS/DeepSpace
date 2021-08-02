package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import javax.inject.Inject


class ViewAllPostViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), ViewAllPostViewModel {

    override suspend fun getAllPosts(): LiveData<Data<out ArrayList<Post>>> {
        return repository.getAllPosts()
    }

    override suspend fun loadImage(postId: String, imageId: String): LiveData<Data<out Bitmap>> {
        return repository.loadImage(postId, imageId)
    }

    override suspend fun getAdditionalData(postId: String): LiveData<Data<out ArrayList<PostView>>> {
        return  repository.getAdditionalData(postId)
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}