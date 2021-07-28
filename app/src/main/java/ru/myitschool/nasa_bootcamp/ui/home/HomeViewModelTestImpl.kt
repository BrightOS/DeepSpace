package ru.myitschool.nasa_bootcamp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.EventModel
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.utils.Resource

class HomeViewModelTestImpl : ViewModel(), HomeViewModel {
    override fun getImageOfTheDayModel(): LiveData<Resource<ImageOfTheDayModel>> {
        return MutableLiveData(Resource.loading(null))
    }

    override fun getArticles(): LiveData<Resource<List<ArticleModel>>> {
        return MutableLiveData(
            Resource.success(
                data = listOf(
                    ArticleModel(title = "Something happened"),
                    ArticleModel(title = "Wow"),
                    ArticleModel(title = "Very very very very very very very very very very very very very very very very very very very very very very long title")
                )
            )
        )
    }

    override fun getEvents(): LiveData<Resource<List<EventModel>>> {
        return MutableLiveData(
            Resource.success(
                data = listOf(
                    EventModel(title = "Something happened"),
                    EventModel(title = "Wow"),
                    EventModel(title = "Very very very very very very very very very very very very very very very very very very very very very very long title")
                )
            )
        )
    }
}