package ru.myitschool.nasa_bootcamp.data.repository

import com.example.firstkotlinapp.model.apod.Apod
import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid

interface AsteroidRepository2 {
    suspend fun getAsteroids(beginDate: String, endDate: String): Response<Asteroid>

}