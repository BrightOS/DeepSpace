package ru.myitschool.nasa_bootcamp.data.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.dto.firebase.ImagePost
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.dto.firebase.TextPost
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.data.model.SubComment
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import ru.myitschool.nasa_bootcamp.ui.user_create_post.CreatePostRecyclerAdapter
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.downloadFirebaseImage
import java.util.*
import kotlin.collections.ArrayList

class FirebaseRepositoryImpl : FirebaseRepository {
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val dbInstance = FirebaseDatabase.getInstance()


    // VIEW CUSTOM USER POSTS
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

    override suspend fun downloadImage(
        postId: String,
        imageId: String
    ): LiveData<Data<out Bitmap>> {
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


    // USER CUSTOM POST CREATION:

    override suspend fun createPost(post: Post, postId: String): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        post.author = MFirebaseUser().getUser().uid
        post.dateCreated = Date().time
        try {
            dbInstance.getReference("user_posts").child(postId).setValue(post).await()
            returnData.postValue(Data.Ok("Ok"))
        } catch (e: java.lang.Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun uploadImage(
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
        } catch (e: java.lang.Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun getLastPostId(): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        dbInstance.getReference("user_posts").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val count = snapshot.children.last().key!!.toLong() + 1
                        returnData.postValue(Data.Ok(count.toString()))
                    } catch (e: NoSuchElementException) {
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


    // COMMENTS AND LIKES OPERATIONS:

    override suspend fun pushComment(
        source: String,
        postId: Int,
        comment: String
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null) {
            try {
                val commentId = getLastCommentId(source, postId) + 1
                val commentObject =
                    Comment(
                        commentId,
                        comment,
                        listOf(),
                        listOf(),
                        authenticator.uid!!,
                        Date().time
                    )
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("comments")
                    .child(commentId.toString()).setValue(commentObject).await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated."))
        }
        return returnData
    }

    override suspend fun pushSubComment(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        comment: String
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null) {
            try {
                val subCommentId = getLastSubCommentId(source, postId, fatherCommentId) + 1
                val subCommentObject = SubComment(
                    subCommentId, fatherCommentId, comment, listOf(),
                    authenticator.uid!!, Date().time
                )
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("comments")
                    .child(fatherCommentId.toString()).child("subComments")
                    .child(subCommentId.toString())
                    .setValue(subCommentObject).await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated."))
        }
        return returnData
    }

    override suspend fun deleteComment(
        source: String,
        postId: Int,
        commentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && authenticator.uid == getCommentAuthor(
                source,
                postId,
                commentId
            )
        ) {
            try {
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("comments")
                    .child(commentId.toString()).removeValue().await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error("Comment doesn't exist"))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or he is not author of the comment"))
        }
        return returnData
    }

    override suspend fun deleteSubComment(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && authenticator.uid == getSubCommentAuthor(
                source,
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            try {
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("comments")
                    .child(fatherCommentId.toString()).child("subComments")
                    .child(subCommentId.toString()).removeValue().await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: java.lang.Exception) {
                returnData.postValue(Data.Error("SubComment doesn't exist"))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or he is not author of the SubComment"))
        }
        return returnData
    }

    override suspend fun pushLike(source: String, postId: Int): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && !checkIfHasLike(source, postId)) {
            try {
                dbInstance.getReference("posts").child(postId.toString()).child("likes")
                    .child(authenticator.uid!!)
                    .setValue(authenticator.uid).await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or already has a like"))
        }
        return returnData
    }

    override suspend fun pushLikeForComment(
        source: String,
        postId: Int,
        commentId: Long
    ): LiveData<Data<out String>> {
        TODO("Not yet implemented")
    }

    override suspend fun pushLikeForSubComment(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLike(source: String, postId: Int): LiveData<Data<out String>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCommentLike(
        source: String,
        postId: Int,
        commentId: Long
    ): LiveData<Data<out String>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSubCommentLike(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
        TODO("Not yet implemented")
    }

    private suspend fun getUsernameById(uid: String): String =
        try {
            dbInstance.getReference("user_data").child(uid).child("username").get().await()
                .getValue(String::class.java)!!
        } catch (e: Exception) {
            "DELETED USER"
        }

    private suspend fun getLastCommentId(source: String, postId: Int): Long {
        return try {
            dbInstance.getReference("posts").child(source).child(postId.toString())
                .child("comments").get()
                .await().children.last().key!!.toLong()
        } catch (e: java.lang.Exception) {
            1
        }
    }

    private suspend fun getLastSubCommentId(
        source: String,
        postId: Int,
        fatherCommentId: Long
    ): Long {
        return try {
            dbInstance.getReference("posts").child(source).child(postId.toString())
                .child("comments")
                .child(fatherCommentId.toString()).child("subComments")
                .get()
                .await().children.last().key!!.toLong()
        } catch (e: java.lang.Exception) {
            1
        }
    }


    suspend fun getLikeCount(source: String, postId: Int): Long {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("likes").get()
            .await().childrenCount
    }

    suspend fun checkIfHasLike(source: String, postId: Int): Boolean {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("likes").get()
            .await()
            .hasChild(authenticator.uid!!)
    }

    suspend fun checkIfHasCommentLike(source: String, postId: Int, commentId: Long): Boolean {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("comments")
            .child(commentId.toString()).child("likes").get().await()
            .hasChild(authenticator.uid!!)
    }

    suspend fun checkIfHasSubCommentLike(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): Boolean {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("comments")
            .child(fatherCommentId.toString()).child("subComments").child(subCommentId.toString())
            .child("likes").get().await()
            .hasChild(authenticator.uid!!)
    }

    suspend fun getCommentAuthor(source: String, postId: Int, commentId: Long): String? {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("comments")
            .child(commentId.toString()).child("userId").get().await().getValue(String::class.java)
    }

    suspend fun getSubCommentAuthor(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): String? {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("comments")
            .child(fatherCommentId.toString()).child("subComments").child(subCommentId.toString())
            .child("userId").get().await().getValue(String::class.java)
    }
}