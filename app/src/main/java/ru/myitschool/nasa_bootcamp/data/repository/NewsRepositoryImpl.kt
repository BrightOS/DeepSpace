package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.api.NewsApi
import ru.myitschool.nasa_bootcamp.data.dto.news.Article
import javax.inject.Inject

class NewsRepositoryImpl  @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNews(): Response<ArrayList<Article>> = newsApi.getArticles()

}