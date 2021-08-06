package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.news.Article
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.utils.Resource

interface NewsRepository {
    suspend fun getNews() : Resource<List<ArticleModel>>
}