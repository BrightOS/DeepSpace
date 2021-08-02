package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.ImagePost
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.data.model.PostView
import ru.myitschool.nasa_bootcamp.data.model.TextPost
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import ru.myitschool.nasa_bootcamp.ui.user_create_post.CreatePostRecyclerAdapter
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.downloadFirebaseImage
import java.io.File
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