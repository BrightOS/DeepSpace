package ru.myitschool.nasa_bootcamp.ui.nasa.pages.newsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.news.Article
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

interface NewsViewModel {
    suspend fun getNews()
    fun getViewModelScope(): CoroutineScope
    fun getArticlesList(): MutableLiveData<ArrayList<ArticleModel>>
}