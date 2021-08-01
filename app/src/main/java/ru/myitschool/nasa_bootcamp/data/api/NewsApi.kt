package ru.myitschool.nasa_bootcamp.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.myitschool.nasa_bootcamp.data.dto.news.Article
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch

interface NewsApi {
    @GET("articles")
    suspend fun getArticles(): Response<List<Article>>
}