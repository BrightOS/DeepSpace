package ru.myitschool.nasa_bootcamp.ui.home.social_media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.fb_general.BlogPagingSource
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.data.repository.SocialMediaRepository
import ru.myitschool.nasa_bootcamp.utils.BLOG_PAGE_SIZE
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SocialMediaViewModelImpl @Inject constructor(private val networkRepository: SocialMediaRepository) :
    SocialMediaViewModel, ViewModel() {
    private val blogsFlow = Pager(PagingConfig(pageSize = BLOG_PAGE_SIZE)) {
        networkRepository.getBlogPagingSource()
    }.flow.cachedIn(getViewModelScope())
    private val articles =
        MutableLiveData<Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>>()
    private var selectedPost: LiveData<ContentWithLikesAndComments<PostModel>>? = null
    private var selectedArticle: LiveData<ContentWithLikesAndComments<ArticleModel>>? = null
    private val currentUser = MutableLiveData<UserModel?>(null)

    override fun getBlogs() = blogsFlow
    override fun getNews() = articles


    override suspend fun loadNews() {
        articles.postValue(Resource.loading(articles.value?.data))
        articles.postValue(networkRepository.getNews())
    }

    override suspend fun loadCurrentUser() {
        currentUser.postValue(networkRepository.getCurrentUser())
    }

    override fun getViewModelScope() = viewModelScope
    override fun setSelectedPost(post: LiveData<ContentWithLikesAndComments<PostModel>>) {
        selectedArticle = null
        selectedPost = post
    }

    override fun getSelectedPost() = selectedPost
    override fun setSelectedArticle(article: LiveData<ContentWithLikesAndComments<ArticleModel>>) {
        selectedPost = null
        selectedArticle = article
    }

    override fun getSelectedArticle() = selectedArticle

    override suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    ): Resource<Nothing> {
        return networkRepository.pressedLikeOnComment(item, comment)
    }

    override suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing> {
        return networkRepository.pressedLikeOnItem(item)
    }

    override suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment?
    ): Resource<Nothing> {
        return networkRepository.sendComment(message, id, _class, parentComment)
    }

    override fun getCurrentUser() = currentUser
    override suspend fun createPost(title: String, postItems: List<Any>): Resource<Nothing> {
        return networkRepository.createPost(title, postItems)
    }

    override suspend fun deleteComment(
        comment: Comment,
        item: ContentWithLikesAndComments<out Any>
    ): Resource<Nothing> = networkRepository.deleteComment(comment, item)
}