package ru.myitschool.nasa_bootcamp.data.repository

import ru.myitschool.nasa_bootcamp.data.api.NewsApi
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNews(): Resource<List<ArticleModel>> {
        return try {
            val response = newsApi.getArticles()
            if (response.body() != null)
                Resource.success(
                    response.body()!!.map { dto -> dto.createArticleModel() }
                )
            else Resource.error("Empty response body", null)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e.message.toString(), null)
        }
    }
}