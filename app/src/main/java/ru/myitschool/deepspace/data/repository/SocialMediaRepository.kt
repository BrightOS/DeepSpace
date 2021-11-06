package ru.myitschool.deepspace.data.repository

import androidx.lifecycle.LiveData
import ru.myitschool.deepspace.data.fb_general.BlogPagingSource
import ru.myitschool.deepspace.data.model.*
import ru.myitschool.deepspace.utils.Resource

interface SocialMediaRepository {
    suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>
    suspend fun getBlogPosts(): Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>>
    fun getBlogPagingSource(): BlogPagingSource
    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing>
    suspend fun getCurrentUser(): UserModel?
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
    suspend fun createPost(title: String, postItems: List<Any>): Resource<Nothing>
    suspend fun deleteComment(comment: Comment, item: ContentWithLikesAndComments<out Any>): Resource<Nothing>
}