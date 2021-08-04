package ru.myitschool.nasa_bootcamp.ui.home.social_media

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.data.repository.NetworkRepository
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SocialMediaViewModelImpl @Inject constructor(val networkRepository: NetworkRepository) :
    SocialMediaViewModel, ViewModel() {
    private val blogs = MutableLiveData<Resource<List<ContentWithLikesAndComments<PostModel>>>>()
    private val news = MutableLiveData<Resource<List<ContentWithLikesAndComments<ArticleModel>>>>()

    override fun getBlogs() = blogs
    override fun getNews() = news
    override suspend fun loadBlogs() {
        blogs.postValue(networkRepository.getBlogPosts())
    }

    override suspend fun loadNews() {
        news.postValue(networkRepository.getNews())
    }

    override fun getViewModelScope() = viewModelScope
}