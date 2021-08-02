package ru.myitschool.nasa_bootcamp.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.dto.firebase.ImagePost
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.dto.firebase.TextPost
import ru.myitschool.nasa_bootcamp.ui.user_create_post.CreatePostRecyclerAdapter
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.downloadFirebaseImage

class FirebaseRepositoryImpl : FirebaseRepository {
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val dbInstance = FirebaseDatabase.getInstance()
    override suspend fun getAllPosts(): LiveData<Data<out ArrayList<Post>>> {
        val returnData = MutableLiveData<Data<out ArrayList<Post>>>()
        val allPosts = ArrayList<Post>()
        try {
            dbInstance.getReference("user_posts").get().await().children.forEach {
                val username = getUsernameById(it.child("author").getValue(String::class.java)!!)
                val postData = Post(
                    it.key!!,
                    it.child("title").getValue(String::class.java)!!,
                    it.child("dateCreated").getValue(Long::class.java)!!,
                    username,
                    null
                )
                allPosts.add(postData)
            }
            returnData.postValue(Data.Ok(allPosts))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    override suspend fun loadImage(postId: String, imageId: String): LiveData<Data<out Bitmap>> {
        val storageRef = storage.getReference("posts/$postId/$imageId")
        return downloadFirebaseImage(storageRef)
    }

    override suspend fun getAdditionalData(postId: String): LiveData<Data<out ArrayList<PostView>>> {
        val returnData = MutableLiveData<Data<out ArrayList<PostView>>>()
        val postViewList = ArrayList<PostView>()
        try {
            dbInstance.getReference("user_posts").child(postId).child("data").get()
                .await().children.forEach {
                    val type: Int = it.child("_type").getValue(Int::class.java)!!
                    val id: Int = it.child("_id").getValue(Int::class.java)!!
                    if (type == CreatePostRecyclerAdapter.IMAGE) {
                        val imagePath: String = it.child("imagePath").getValue(String::class.java)!!
                        postViewList.add(ImagePost(id, type, imagePath))
                    } else {
                        val text: String = it.child("text").getValue(String::class.java)!!
                        postViewList.add(TextPost(id, type, text))
                    }
                }
            returnData.postValue(Data.Ok(postViewList))
        } catch (e: Exception) {
            e.printStackTrace()
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    private suspend fun getUsernameById(uid: String): String =
        try {
            dbInstance.getReference("user_data").child(uid).child("username").get().await()
                .getValue(String::class.java)!!
        } catch (e: Exception) {
            "DELETED USER"
        }


}