package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.utils.Data
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*

class CreatePostViewModelImpl : ViewModel(), CreatePostViewModel {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var dbReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("user_posts")

    override suspend fun createPost(post: Post, postId: String): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        post.author = MFirebaseUser().getUser().uid
        post.dateCreated = Date().time
        try {
            dbReference.child(postId).setValue(post).await()
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
                returnData.postValue(Data.Ok("Ok"))
            }.addOnFailureListener {
                returnData.postValue(Data.Error(it.message!!))
            }
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun getLastPostId(): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        dbReference.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val count = snapshot.children.last().key!!.toLong() + 1
                    returnData.postValue(Data.Ok(count.toString()))
                }
                catch (e: NoSuchElementException) {
                    // if node has no children
                    returnData.postValue(Data.Ok("0"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                returnData.postValue(Data.Error(error.message))
            }

        })
        return returnData
    }
}