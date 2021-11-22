package ru.myitschool.deepspace.ui.home.social_media

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.myitschool.deepspace.data.model.*
import ru.myitschool.deepspace.utils.Resource

/*
 * @author Samuil Nalisin
 */
interface SocialMediaViewModel {
    val createPostStatus: LiveData<Resource<Unit>>
    val pressedOnLikeStatus: LiveData<Resource<Unit>>
    fun getNews(): LiveData<Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>>
    suspend fun loadNews()
    suspend fun loadCurrentUser()
    fun getViewModelScope(): CoroutineScope
    fun setSelectedPost(post: LiveData<ContentWithLikesAndComments<PostModel>>)
    fun getSelectedPost(): LiveData<ContentWithLikesAndComments<PostModel>>?
    fun setSelectedArticle(article: LiveData<ContentWithLikesAndComments<ArticleModel>>)
    fun getSelectedArticle(): LiveData<ContentWithLikesAndComments<ArticleModel>>?
    suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    )

    suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>)
    suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment? = null
    ): Resource<Nothing>

    fun getCurrentUser(): LiveData<UserModel?>
    suspend fun createPost(title: String, postItems: List<Any>)
    suspend fun deleteComment(
        comment: Comment,
        item: ContentWithLikesAndComments<out Any>
    ): Resource<Nothing>
//    suspend fun getCommentsByItemId(id: Long, _class: Class<*>): List<LiveData<Comment>>
//    suspend fun getLikesByItemId(id: Long, _class: Class<*>): List<UserModel>
//    suspend fun getLikesByCommentId(id: Long): List<UserModel>
//    suspend fun getCommentById(id: Long): LiveData<Comment>
    fun getBlogs():  Flow<PagingData<LiveData<ContentWithLikesAndComments<PostModel>>>>
}
