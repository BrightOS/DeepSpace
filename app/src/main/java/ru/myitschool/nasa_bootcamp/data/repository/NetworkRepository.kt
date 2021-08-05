package ru.myitschool.nasa_bootcamp.data.repository

import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource

interface NetworkRepository {
    suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>>
    suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>>
    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing>
    suspend fun pressedLikeOnComment(comment: Comment): Resource<Nothing>
    suspend fun sendComment(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment? = null
    ): Resource<Nothing>

    fun getCurrentUser(): UserModel
}