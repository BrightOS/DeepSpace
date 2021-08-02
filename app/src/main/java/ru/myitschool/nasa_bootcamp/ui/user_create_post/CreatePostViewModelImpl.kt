package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import javax.inject.Inject

class CreatePostViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), CreatePostViewModel {

    override suspend fun createPost(post: Post, postId: String): LiveData<Data<out String>> {
        return repository.createPost(post, postId)
    }

    override fun uploadImage(
        postId: String,
        imageId: Int,
        imagePath: Uri
    ): LiveData<Data<out String>> {
        return repository.uploadImage(postId, imageId, imagePath)
    }

    override fun getLastPostId(): LiveData<Data<out String>> {
        return repository.getLastPostId()
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}