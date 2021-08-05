package ru.myitschool.nasa_bootcamp.ui.home.social_media

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.data.repository.NetworkRepository
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SocialMediaViewModelImpl @Inject constructor(private val networkRepository: NetworkRepository) :
    SocialMediaViewModel, ViewModel() {
    private val blogs =
        MutableLiveData<Resource<List<ContentWithLikesAndComments<PostModel>>>>()
    private val articles =
        MutableLiveData<Resource<List<ContentWithLikesAndComments<ArticleModel>>>>()
    private var selectedPost: ContentWithLikesAndComments<PostModel>? = null
    private var selectedArticle: ContentWithLikesAndComments<ArticleModel>? = null

    override fun getBlogs() = blogs
    override fun getNews() = articles

    override suspend fun loadBlogs() {
        blogs.postValue(networkRepository.getBlogPosts())
    }

    override suspend fun loadNews() {
        articles.postValue(networkRepository.getNews())
    }

    override fun getViewModelScope() = viewModelScope

    override fun setSelectedPost(post: ContentWithLikesAndComments<PostModel>) {
        selectedPost = post
    }

    override fun getSelectedPost() = selectedPost

    override fun setSelectedArticle(article: ContentWithLikesAndComments<ArticleModel>) {
        selectedArticle = article
    }

    override fun getSelectedArticle() = selectedArticle
    override suspend fun pressedLikeOnPost(post: ContentWithLikesAndComments<PostModel>): Resource<Nothing> {
        return networkRepository.pressedLikeOnPost(post)
    }

    override suspend fun pressedLikeOnComment(comment: MutableLiveData<Comment>): Resource<Nothing> {
        return networkRepository.pressedLikeOnComment(comment)
    }

    override suspend fun pressedLikeOnArticle(article: ContentWithLikesAndComments<ArticleModel>): Resource<Nothing> {
        return networkRepository.pressedLikeOnArticle(article)
    }

    override suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>
    ): Resource<Nothing> {
        return networkRepository.sendMessage(message, id, _class)
    }


}