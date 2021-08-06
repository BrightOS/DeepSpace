package ru.myitschool.nasa_bootcamp.data.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.model.UserModel
import ru.myitschool.nasa_bootcamp.utils.Data

interface FirebaseRepository {
    suspend fun getAllPosts(): Data<out ArrayList<Post>>
    suspend fun downloadImage(postId: String, imageId: String): LiveData<Data<out Bitmap>>
    suspend fun getAdditionalData(postId: String): LiveData<Data<out ArrayList<PostView>>>
    suspend fun createPost(post: Post, postId: String): LiveData<Data<out String>>
    fun uploadImage(postId: String, imageId: Int,  imagePath: Uri) : LiveData<Data<out String>>
    fun getLastPostId():  LiveData<Data<out String>>
    suspend fun pushComment(source: String, postId: Int, comment: String): LiveData<Data<out String>>
    suspend fun pushSubComment(source: String, postId: Int, fatherCommentId: Long, comment: String): LiveData<Data<out String>>
    suspend fun deleteComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun deleteSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    suspend fun pushLike(source: String, postId: Int): LiveData<Data<out String>>
    suspend fun pushLikeForComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun pushLikeForSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    suspend fun deleteLike(source: String, postId: Int): LiveData<Data<out String>>
    suspend fun deleteCommentLike(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun deleteSubCommentLike(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    suspend fun authenticateUser(email: String, password: String): LiveData<Data<out FirebaseUser>>
    fun signOutUser(): LiveData<Data<out String>>
    suspend fun createUser(userName: String, email: String, password: String, imagePath: Uri?): LiveData<Data<out FirebaseUser>>
    suspend fun getUser(uid: String): LiveData<Data<out UserModel>>
}