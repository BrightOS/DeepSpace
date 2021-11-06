package ru.myitschool.deepspace.ui.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.ArticleModel
import ru.myitschool.deepspace.data.model.ImageOfTheDayModel
import ru.myitschool.deepspace.utils.Resource

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
