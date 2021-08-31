package ru.myitschool.nasa_bootcamp.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.Resource

interface FirebaseRepository {
    suspend fun getPostsFromDataSnapshot(snapshot: DataSnapshot): Resource<List<MutableLiveData<ContentWithLikesAndComments<PostModel>>>>
    suspend fun getAllPostsRawData(): Resource<List<MutableLiveData<ContentWithLikesAndComments<PostModel>>>>
    suspend fun getAllPosts(): Data<ArrayList<Post>>
    suspend fun downloadImage(postId: String, imageId: String):Data<Bitmap>
    suspend fun createPost(title: String, postItems: List<Any>): Resource<MutableLiveData<ContentWithLikesAndComments<PostModel>>>
    fun uploadImage(postId: String, imageId: Int,  imagePath: Uri) : LiveData<Data<String>>
    suspend fun getLastPostId(): String?
    suspend fun pushComment(source: String, postId: Int, comment: String): Resource<Nothing>
    suspend fun pushSubComment(source: String, postId: Int, fatherCommentId: Long, comment: String): Resource<Nothing>
    suspend fun deleteComment(source: String, postId: Int, commentId: Long): Resource<Nothing>
    suspend fun deleteSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): Resource<Nothing>
    suspend fun pushLike(source: String, postId: Int): Resource<Nothing>
    suspend fun pushLikeForComment(source: String, postId: Int, commentId: Long): Resource<Nothing>
    suspend fun pushLikeForSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): Resource<Nothing>
    suspend fun deleteLike(source: String, postId: Int): Resource<Nothing>
    suspend fun deleteCommentLike(source: String, postId: Int, commentId: Long): Resource<Nothing>
    suspend fun deleteSubCommentLike(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): Resource<Nothing>
    suspend fun authenticateUser(context: Context, email: String, password: String): Data< FirebaseUser>
    fun signOutUser(context: Context): LiveData<Data<String>>
    suspend fun createUser(context: Context, userName: String, email: String, password: String, imagePath: Uri?): Data<FirebaseUser>
    suspend fun getUser(uid: String): UserModel?
    fun getCurrentUser(): UserModel?
    suspend fun getArticleModelComments(postId: Long): List<Comment>
    suspend fun getArticleModelLikes(postId: Long): List<UserModel>
    fun articleModelEventListener(articleModel: MutableLiveData<ContentWithLikesAndComments<ArticleModel>>, postId: Long)
}