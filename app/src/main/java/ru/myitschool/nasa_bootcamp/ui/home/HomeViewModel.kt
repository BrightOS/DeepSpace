package ru.myitschool.nasa_bootcamp.ui.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.EventModel
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.utils.Resource

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
