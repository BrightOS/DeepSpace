package ru.myitschool.nasa_bootcamp.data.repository

import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.utils.Resource

interface NetworkRepository {
    suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>>
    suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>>
}