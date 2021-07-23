package ru.myitschool.nasa_bootcamp.data.api

import com.example.firstkotlinapp.model.apod.Apod
import com.example.firstkotlinapp.model.rovers.Rovers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.myitschool.nasa_bootcamp.utils.API_KEY

interface NasaApi {

    // Изображение дня от НАСА
    @GET("planetary/apod")
    suspend fun getAstronomyImageOfTheDay(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Apod>

    //Фотографии роверов с Марса
    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=2")
    suspend fun getRoverPhotos(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Rovers>

    //https://api.nasa.gov/
    @GET("neo/rest/v1/feed?")
    suspend fun getAsteroidInfo(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): String
}