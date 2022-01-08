package ru.berserkers.deepspace.data.repository

import ru.berserkers.deepspace.data.model.ArticleModel
import ru.berserkers.deepspace.utils.Resource

/*
 * @author Yana Glad
 */
interface NewsRepository {
    suspend fun getNews() : Resource<List<ArticleModel>>
}
