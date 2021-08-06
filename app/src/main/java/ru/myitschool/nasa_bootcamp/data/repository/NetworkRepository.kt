package ru.myitschool.nasa_bootcamp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource

interface NetworkRepository {
    suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>
    suspend fun getBlogPosts(): Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>>
    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing>
    fun getCurrentUser(context: Context): UserModel
    suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    ): Resource<Nothing>
    suspend fun sendComment(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment? = null
    ): Resource<Nothing>
    suspend fun getUser(uid: String): UserModel?
}