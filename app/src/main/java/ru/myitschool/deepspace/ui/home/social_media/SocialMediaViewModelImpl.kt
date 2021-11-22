package ru.myitschool.deepspace.ui.home.social_media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.deepspace.data.model.*
import ru.myitschool.deepspace.data.repository.SocialMediaRepository
import ru.myitschool.deepspace.utils.BLOG_PAGE_SIZE
import ru.myitschool.deepspace.utils.Resource
import javax.inject.Inject

/*
 * @author Samuil Nalisin
 */
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
    private val _createPostStatus = MutableLiveData<Resource<Unit>>()
    override val createPostStatus: LiveData<Resource<Unit>> = _createPostStatus
    private val _pressedOnLikeStatus = MutableLiveData<Resource<Unit>>()
    override val pressedOnLikeStatus: LiveData<Resource<Unit>> = _pressedOnLikeStatus
    private val _sendMessageStatus = MutableLiveData<Resource<Unit>>()
    override val sendMessageStatus: LiveData<Resource<Unit>> = _sendMessageStatus
    private val _deleteCommentStatus = MutableLiveData<Resource<Unit>>()
    override val deleteCommentStatus: LiveData<Resource<Unit>> = _deleteCommentStatus

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
    ) {
        _pressedOnLikeStatus.postValue(Resource.loading(null))
        _pressedOnLikeStatus.postValue(networkRepository.pressedLikeOnComment(item, comment))
    }

    override suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>) {
        _pressedOnLikeStatus.postValue(Resource.loading(null))
        _pressedOnLikeStatus.postValue(networkRepository.pressedLikeOnItem(item))
    }

    override suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment?
    ) {
        _sendMessageStatus.postValue(Resource.loading(null))
        _sendMessageStatus.postValue(
            networkRepository.sendComment(
                message,
                id,
                _class,
                parentComment
            )
        )
    }

    override fun getCurrentUser() = currentUser
    override suspend fun createPost(title: String, postItems: List<Any>) {
        _createPostStatus.postValue(Resource.loading(null))
        _createPostStatus.postValue(networkRepository.createPost(title, postItems))
    }

    override suspend fun deleteComment(
        comment: Comment,
        item: ContentWithLikesAndComments<out Any>
    ) {
        _deleteCommentStatus.postValue(Resource.loading(null))
        _deleteCommentStatus.postValue(networkRepository.deleteComment(comment, item))
    }
}