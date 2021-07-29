package ru.myitschool.nasa_bootcamp.ui.home

import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.EventModel
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.utils.Resource

interface HomeViewModel {
    fun getImageOfTheDayModel(): LiveData<Resource<ImageOfTheDayModel>>
    fun getArticles(): LiveData<Resource<List<ArticleModel>>>
    fun getEvents(): LiveData<Resource<List<EventModel>>>
}