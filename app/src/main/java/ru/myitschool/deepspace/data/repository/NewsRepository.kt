package ru.myitschool.deepspace.data.repository

import ru.myitschool.deepspace.data.model.ArticleModel
import ru.myitschool.deepspace.utils.Resource

/*
 * @author Yana Glad
 */
interface NewsRepository {
    suspend fun getNews() : Resource<List<ArticleModel>>
}