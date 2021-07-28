package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.news.Article

interface NewsRepository {
    suspend fun getNews() : Response<ArrayList<Article>>
}