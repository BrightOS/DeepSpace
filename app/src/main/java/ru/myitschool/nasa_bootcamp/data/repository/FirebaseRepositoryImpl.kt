package ru.myitschool.nasa_bootcamp.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.dto.firebase.*
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.ui.user_create_post.CreatePostRecyclerAdapter
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.downloadFirebaseImage
import java.util.*
import kotlin.collections.ArrayList


class FirebaseRepositoryImpl : FirebaseRepository {
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val dbInstance = FirebaseDatabase.getInstance()

    private val sharedPreferencesFileName = "currentUser"
    private val sharedPreferencesUserName = "userName"
    private val sharedPreferencesUri = "uri"
    private val sharedPreferencesId = "uid"


    // VIEW CUSTOM USER POSTS
    override suspend fun getAllPosts(): Data<ArrayList<Post>> {
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
            return Data.Ok(allPosts)
        } catch (e: Exception) {
            return Data.Error(e.message.toString())
        }
    }

    override suspend fun getAllPostsRawData(): LiveData<ContentWithLikesAndComments<PostModel>> {
        TODO("Not yet implemented")
    }

    private fun getAllUserPostComments(postId: Int) {

    }

    private fun getAllUserPostLikes(postId: Int) {

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
                    CommentDto(
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
                println(e.message.toString())
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
                val subCommentObject = SubCommentDto(
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

    override suspend fun pushLike(source: String, postId: Int) {
        if (authenticator.uid != null && !checkIfHasLike(source, postId)) {
            try {
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("likes")
                    .child(authenticator.uid!!)
                    .setValue(authenticator.uid).await()
            } catch (e: Exception) {
            }
        }
        else {
            try {
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("likes")
                    .child(authenticator.uid!!)
                    .setValue(authenticator.uid).await()
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun pushLikeForComment(
        source: String,
        postId: Int,
        commentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && !checkIfHasCommentLike(source, postId, commentId)) {
            try {
                dbInstance.getReference("posts").child(source).child(postId.toString())
                    .child("comments")
                    .child(commentId.toString()).child("likes").child(authenticator.uid!!)
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

    override suspend fun pushLikeForSubComment(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && !checkIfHasSubCommentLike(
                source,
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            try {
                dbInstance.getReference("posts").child(postId.toString()).child("comments")
                    .child(fatherCommentId.toString()).child("subComments")
                    .child(subCommentId.toString()).child("likes").child(authenticator.uid!!)
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

    override suspend fun deleteLike(source: String, postId: Int): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && checkIfHasLike(source, postId)) {
            try {
                dbInstance.getReference("posts").child(postId.toString()).child("likes")
                    .child(authenticator.uid!!).removeValue().await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or he didn't`t like this post"))
        }
        return returnData
    }

    override suspend fun deleteCommentLike(
        source: String,
        postId: Int,
        commentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && checkIfHasCommentLike(source, postId, commentId)) {
            try {
                dbInstance.getReference("posts").child(postId.toString()).child("comments")
                    .child(commentId.toString()).child("likes").child(authenticator.uid!!)
                    .removeValue()
                    .await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or he didn't`t like this comment"))
        }
        return returnData
    }

    override suspend fun deleteSubCommentLike(
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
        val returnData = MutableLiveData<Data<out String>>()
        if (authenticator.uid != null && checkIfHasSubCommentLike(
                source,
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            try {
                dbInstance.getReference("posts").child(postId.toString()).child("comments")
                    .child(fatherCommentId.toString()).child("subComments")
                    .child(subCommentId.toString()).child("likes").child(authenticator.uid!!)
                    .removeValue()
                    .await()
                returnData.postValue(Data.Ok("Ok"))
            } catch (e: Exception) {
                returnData.postValue(Data.Error(e.message.toString()))
            }
        } else {
            returnData.postValue(Data.Error("User is not authenticated or he didn't`t like this subComment"))
        }
        return returnData
    }

    override suspend fun authenticateUser(
        context: Context,
        email: String,
        password: String
    ): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            val user = authenticator.signInWithEmailAndPassword(email, password).await()
            val userInfo = getUser(user.user!!.uid)

            val sharedPreferences =
                context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit()
            sharedPreferences.putString(sharedPreferencesUserName, userInfo!!.name)
            sharedPreferences.putString(sharedPreferencesUri, userInfo.avatarUrl.toString())
            sharedPreferences.putString(sharedPreferencesId, user.user!!.uid)
            sharedPreferences.apply()

            returnData.postValue(Data.Ok(user?.user!!))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    override fun signOutUser(context: Context): LiveData<Data<out String>> {
        val returnData: MutableLiveData<Data<out String>> = MutableLiveData()
        try {
            authenticator.signOut()
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit()
                .clear().apply()
            returnData.postValue(Data.Ok("Ok"))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    override suspend fun createUser(
        context: Context,
        userName: String,
        email: String,
        password: String,
        imagePath: Uri?
    ): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            val user = authenticator.createUserWithEmailAndPassword(email, password).await()
            if (user != null) {
                dbInstance.getReference("user_data").child(user.user!!.uid).child("username")
                    .setValue(userName).await()
                val storageRef = storage.getReference("user_data/${user.user?.uid}")
                if (imagePath != null) {
                    storageRef.putFile(imagePath).await()
                }
                user.user!!.sendEmailVerification().await()

                val sharedPreferences =
                    context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)
                        .edit()
                sharedPreferences.putString(sharedPreferencesUserName, userName)
                sharedPreferences.putString(sharedPreferencesUri, imagePath.toString())
                sharedPreferences.putString(sharedPreferencesId, user.user!!.uid)
                sharedPreferences.apply()

                returnData.postValue(Data.Ok(user.user!!))
            } else {
                returnData.postValue(Data.Error("Unknown error happened."))
            }
        } catch (e: java.lang.Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override suspend fun getUser(uid: String): UserModel? {
        var user: UserModel? = null
        try {
            var userName = ""
            var avatarUrl: Uri? = null
            userName =
                dbInstance.getReference("user_data").child(uid).child("username").get().await()
                    .getValue(String::class.java).toString()
            try {
                avatarUrl = storage.getReference("user_data/${uid}").downloadUrl.await()
            } catch (e: Exception) {
            }
            user = UserModel(userName.toString(), avatarUrl, uid)
        } catch (e: Exception) {
        }
        return user
    }

    override fun getCurrentUser(context: Context): UserModel? {
        val sharedPreferences =
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)
        try {
            val id = sharedPreferences.getString(sharedPreferencesId, null)!!
            val userName = sharedPreferences.getString(sharedPreferencesUserName, null)!!
            val url = sharedPreferences.getString(sharedPreferencesUri, null)!!.toUri()
            return UserModel(userName, url, id)
        } catch (e: Exception) {

        }
        return null
    }

    override fun articleModelEventListener(articleModel: MutableLiveData<ContentWithLikesAndComments<ArticleModel>>) {
        val dbInstance = FirebaseDatabase.getInstance()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        try {
            dbInstance.getReference("posts").child("ArticleModel")
                .child(articleModel.value!!.content.id.toString()).child("id")
                .setValue(articleModel.value!!.content.id.toString())
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        dbInstance.getReference("posts").child("ArticleModel")
            .child(articleModel.value!!.content.id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comments: MutableList<Comment> = mutableListOf()
                    val likes: MutableList<UserModel> = mutableListOf()

                    scope.launch {
                        snapshot.child("comments").children.forEach {
                            val id = it.child("id").getValue(Long::class.java)
                            val text = it.child("comment").getValue(String::class.java)
                            val commentLikes = mutableListOf<UserModel>()
                            it.child("likes").children.forEach { like ->
                                val userModel = getUser(like.getValue(String::class.java)!!)
                                commentLikes.add(userModel!!)
                            }

                            val subComments = mutableListOf<SubComment>()
                            val author = getUser(
                                it.child("userId").getValue(String::class.java).toString()
                            )!!
                            val date = it.child("date").getValue(Long::class.java)
                            comments.add(
                                Comment(id!!, text!!, commentLikes, subComments, author, date!!)
                            )
                        }

                        snapshot.child("likes").children.forEach {
                            val userModel = getUser(it.getValue(String::class.java)!!)
                            likes.add(userModel!!)
                        }

                        articleModel.postValue(
                            ContentWithLikesAndComments(
                                articleModel.value!!.content,
                                likes,
                                comments
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Firebase Error", error.message)
                }
            })
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

    private suspend fun getCommentAuthor(source: String, postId: Int, commentId: Long): String? {
        return dbInstance.getReference("posts").child(source).child(postId.toString())
            .child("comments")
            .child(commentId.toString()).child("userId").get().await().getValue(String::class.java)
    }

    private suspend fun getSubCommentAuthor(
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