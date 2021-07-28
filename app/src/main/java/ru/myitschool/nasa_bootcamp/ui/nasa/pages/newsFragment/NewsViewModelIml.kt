package ru.myitschool.nasa_bootcamp.ui.nasa.pages.newsFragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.news.Article
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.NewsRepository
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModel
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModelImpl
import javax.inject.Inject

@HiltViewModel
class NewsViewModelIml @Inject constructor(
    private val newsRepos: NewsRepository
) : ViewModel(), NewsViewModel {


    var newsModels: MutableLiveData<ArrayList<ArticleModel>> =
        MutableLiveData<ArrayList<ArticleModel>>()
    var list: ArrayList<ArticleModel> = arrayListOf()

    override suspend fun getNews() {
        val response = newsRepos.getNews()
        Log.d("News_tag", " is... ${response.body()!![0].createArticleModel().imageUrl}")

        for (article in response.body()!!) {
            list.add(article.createArticleModel())
        }
        newsModels.value = list
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getArticlesList(): MutableLiveData<ArrayList<ArticleModel>> {
        return newsModels
    }

}