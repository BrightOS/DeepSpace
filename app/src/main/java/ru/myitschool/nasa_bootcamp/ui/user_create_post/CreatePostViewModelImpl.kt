package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.utils.Data
import java.lang.Exception

class CreatePostViewModelImpl : ViewModel(), CreatePostViewModel {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var dbReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("user_posts")

    override suspend fun createPost(post: Post): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        try {
            dbReference.child(getLastPostId()).setValue(post).await()
            returnData.postValue(Data.Ok("Ok"))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun loadImage(
        postId: String,
        imageId: Int,
        imagePath: Uri
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        try {
            val storageRef = storage.getReference("posts").child(postId).child("$imageId")
            storageRef.putFile(imagePath).addOnSuccessListener {
                println("Hahahaha")
                returnData.postValue(Data.Ok("Ok"))
            }.addOnFailureListener {
                returnData.postValue(Data.Error(it.message!!))
            }
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun getLastPostId(): String {
        var count: Long = 0
       dbReference.get().addOnSuccessListener {
           count = it.childrenCount + 1
       }
        return count.toString()
    }
}