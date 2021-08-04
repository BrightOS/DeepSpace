package ru.myitschool.nasa_bootcamp.ui.home.social_media

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.utils.Resource

interface SocialMediaViewModel {
    fun getBlogs(): LiveData<Resource<List<ContentWithLikesAndComments<PostModel>>>>
    fun getNews(): LiveData<Resource<List<ContentWithLikesAndComments<ArticleModel>>>>
    suspend fun loadBlogs()
    suspend fun loadNews()
    fun getViewModelScope(): CoroutineScope
}