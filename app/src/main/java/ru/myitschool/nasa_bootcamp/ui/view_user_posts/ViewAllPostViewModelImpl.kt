package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.data.model.PostView
import ru.myitschool.nasa_bootcamp.utils.Data


class ViewAllPostViewModelImpl : ViewModel(), ViewAllPostViewModel {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var dbInstance: FirebaseDatabase =
        FirebaseDatabase.getInstance()

    override suspend fun getAllPosts(): LiveData<Data<out ArrayList<Post>>> {
        val returnData = MutableLiveData<Data<out ArrayList<Post>>>()
        val allPosts = ArrayList<Post>()
        try {
            dbInstance.getReference("user_posts").get().await().children.forEach {
                val username = getUsernameById(it.child("author").getValue(String::class.java)!!)
                val postData = Post(
                    it.child("title").getValue(String::class.java)!!,
                    it.child("dateCreated").getValue(Long::class.java)!!,
                    username,
                    it.child("data").value as ArrayList<PostView>
                )
                allPosts.add(postData)
            }
            returnData.postValue(Data.Ok(allPosts))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    override suspend fun loadImage(postId: String, imageId: Int) {
        TODO("Not yet implemented")
    }

    private suspend fun getUsernameById(uid: String): String =
        try {
            dbInstance.getReference("user_data").child(uid).child("username").get().await()
                .getValue(
                    String::class.java
                )!!
        } catch (e: Exception) {
            "DELETED USER"
        }
}