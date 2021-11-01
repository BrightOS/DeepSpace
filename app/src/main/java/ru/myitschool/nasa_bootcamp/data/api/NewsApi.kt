package ru.myitschool.nasa_bootcamp.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.myitschool.nasa_bootcamp.data.dto.news.Article

/*
 * @author Yana Glad
 */
interface NewsApi {
    @GET("articles")
    suspend fun getArticles(): Response<List<Article>>
}