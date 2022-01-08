package ru.berserkers.deepspace.ui.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.ArticleModel
import ru.berserkers.deepspace.data.model.ImageOfTheDayModel
import ru.berserkers.deepspace.utils.Resource

/*
 * @author Samuil Nalisin
 */
interface HomeViewModel{
    fun getViewModelScope(): CoroutineScope
    fun getImageOfTheDayModel(): LiveData<Resource<ImageOfTheDayModel>>
    fun getArticles(): LiveData<Resource<List<ArticleModel>>>
    suspend fun loadImageOfTheDay()
    suspend fun loadArticles()
}
