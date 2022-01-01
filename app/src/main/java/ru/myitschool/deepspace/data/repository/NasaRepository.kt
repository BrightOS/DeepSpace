package ru.myitschool.deepspace.data.repository


import com.example.firstkotlinapp.model.apod.Apod
import ru.myitschool.deepspace.data.dto.nasa.rovers.Rovers
import retrofit2.Response
import ru.myitschool.deepspace.data.dto.nasa.asteroids.Asteroid

/*
 * @author Yana Glad
 */
interface NasaRepository {
    suspend fun getImageOfTheDay(): Response<Apod>
    suspend fun getAsteroids(beginDate: String, endDate: String): Response<Asteroid>
    suspend fun getRoverCuriosityPhotos(sol : Int): Response<Rovers>
    suspend fun getRoverOpportunityPhotos(sol : Int): Response<Rovers>
    suspend fun getRoverSpiritPhotos(sol : Int): Response<Rovers>

}