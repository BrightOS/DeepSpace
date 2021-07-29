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
        return MutableLiveData(
            Resource.success(
                ImageOfTheDayModel(
                    title = "The Tulip and Cygnus X-1",
                    copyright = "Carlos Uriarte",
                    date = "2021-07-29",
                    explanation = "This tall telescopic field of view looks out along the plane of our Milky Way Galaxy toward the nebula rich constellation Cygnus the Swan.",
                    hdurl = "",
                    media_type = "",
                    service_version = "",
                    url = "https://apod.nasa.gov/apod/image/2107/sh2_101_04_1024.jpg"
                )
            )
        )
    }

    override fun getArticles(): LiveData<Resource<List<ArticleModel>>> {
        return MutableLiveData(
            Resource.success(
                data = listOf(
                    ArticleModel(title = "Something happened", id = 11, imageUrl = "https://randomwordgenerator.com/img/picture-generator/57e6dc47425aaa14f1dc8460962e33791c3ad6e04e5074417d2e72d6934cc5_640.jpg"),
                    ArticleModel(title = "Wow", id = 12, imageUrl = "https://randomwordgenerator.com/img/picture-generator/57e6d1404b57aa14f1dc8460962e33791c3ad6e04e507440762e79d0974ac3_640.jpg"),
                    ArticleModel(
                        title = "Very very very very very very very very very very very very very very very very very very very very very very long title",
                        id = 13,
                        imageUrl = "https://randomwordgenerator.com/img/picture-generator/51e1dc404c4faa0df7c5d57bc32f3e7b1d3ac3e45658714f7c287ed294_640.jpg"
                    )
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

    override fun reloadImageOfTheDay() {
    }
}