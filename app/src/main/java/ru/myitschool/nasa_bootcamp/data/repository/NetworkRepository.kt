package ru.myitschool.nasa_bootcamp.data.repository

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource

interface NetworkRepository {
    suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>>
    suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>>
    suspend fun pressedLikeOnArticle(item: ContentWithLikesAndComments<ArticleModel>): Resource<Nothing>
    suspend fun pressedLikeOnPost(item: ContentWithLikesAndComments<PostModel>): Resource<Nothing>
    suspend fun pressedLikeOnComment(comment: MutableLiveData<Comment>): Resource<Nothing>
    suspend fun sendMessage(message: String, id: Long, _class: Class<*>): Resource<Nothing>
    fun getCurrentUser(): UserModel
}