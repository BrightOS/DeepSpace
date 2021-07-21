package ru.myitschool.nasa_bootcamp.data

import com.example.firstkotlinapp.model.apod.Apod
import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import javax.inject.Inject

interface MainRepository {
    suspend fun getImageOfTheDay(): Response<Apod>
}