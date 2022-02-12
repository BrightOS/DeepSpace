package ru.berserkers.deepspace.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.berserkers.deepspace.data.dto.news.Article

/*
 * @author Yana Glad
 */
interface NewsApi {

    @GET("articles")
    suspend fun getArticles(): Response<List<Article>>
}
