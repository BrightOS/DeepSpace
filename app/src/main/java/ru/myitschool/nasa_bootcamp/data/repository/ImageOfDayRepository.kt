package ru.myitschool.nasa_bootcamp.data.repository


import com.example.firstkotlinapp.model.apod.Apod
import retrofit2.Response

interface ImageOfDayRepository {
    suspend fun getImageOfTheDay(): Response<Apod>
 }