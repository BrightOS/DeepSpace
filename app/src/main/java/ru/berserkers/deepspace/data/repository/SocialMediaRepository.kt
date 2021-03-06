package ru.berserkers.deepspace.data.repository

import androidx.lifecycle.LiveData
import ru.berserkers.deepspace.data.fb_general.BlogPagingSource
import ru.berserkers.deepspace.data.model.*
import ru.berserkers.deepspace.utils.Resource

/*
 * @author Samuil Nalisin
 */
interface SocialMediaRepository {
    suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>
    suspend fun getBlogPosts(): Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>>
    fun getBlogPagingSource(): BlogPagingSource
    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Unit>
    suspend fun getCurrentUser(): UserModel?
    suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    ): Resource<Unit>
    suspend fun sendComment(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment? = null
    ): Resource<Unit>
    suspend fun getUser(uid: String): UserModel?
    suspend fun createPost(title: String, postItems: List<Any>): Resource<Unit>
    suspend fun deleteComment(comment: Comment, item: ContentWithLikesAndComments<out Any>): Resource<Unit>
}
