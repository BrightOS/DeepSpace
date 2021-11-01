package ru.myitschool.nasa_bootcamp.data.api

import com.example.firstkotlinapp.model.apod.Apod
import ru.myitschool.nasa_bootcamp.data.dto.nasa.rovers.Rovers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.utils.API_KEY

/*
 * @author Yana Glad
 */
interface NasaApi {

    // Изображение дня от НАСА
    @GET("planetary/apod")
    suspend fun getAstronomyImageOfTheDay(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Apod>

    //Фотографии роверов с Марса
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getRoverCuriosityPhotos(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("sol") sol: Int
        ): Response<Rovers>

    @GET("mars-photos/api/v1/rovers/opportunity/photos")
    suspend fun getRoverOpportunityPhotos(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("sol") sol: Int
    ): Response<Rovers>

    @GET("mars-photos/api/v1/rovers/spirit/photos")
    suspend fun getRoverSpiritPhotos(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("sol") sol: Int
    ): Response<Rovers>

    //https://api.nasa.gov/
    @GET("neo/rest/v1/feed?")
    suspend fun getAsteroidInfo(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Asteroid>
    
}