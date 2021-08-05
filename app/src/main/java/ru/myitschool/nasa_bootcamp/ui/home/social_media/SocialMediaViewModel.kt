package ru.myitschool.nasa_bootcamp.ui.home.social_media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.utils.Resource

interface SocialMediaViewModel {
    fun getBlogs(): LiveData<Resource<List<ContentWithLikesAndComments<PostModel>>>>
    fun getNews(): LiveData<Resource<List<ContentWithLikesAndComments<ArticleModel>>>>
    suspend fun loadBlogs()
    suspend fun loadNews()
    fun getViewModelScope(): CoroutineScope
    fun setSelectedPost(post: ContentWithLikesAndComments<PostModel>)
    fun getSelectedPost(): ContentWithLikesAndComments<PostModel>?
    fun setSelectedArticle(article: ContentWithLikesAndComments<ArticleModel>)
    fun getSelectedArticle(): ContentWithLikesAndComments<ArticleModel>?
    suspend fun pressedLikeOnComment(comment: MutableLiveData<Comment>): Resource<Nothing>
    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing>
    suspend fun sendMessage(message: String, id: Long, _class: Class<*>): Resource<Nothing>
//    suspend fun getCommentsByItemId(id: Long, _class: Class<*>): List<LiveData<Comment>>
//    suspend fun getLikesByItemId(id: Long, _class: Class<*>): List<UserModel>
//    suspend fun getLikesByCommentId(id: Long): List<UserModel>
//    suspend fun getCommentById(id: Long): LiveData<Comment>
}