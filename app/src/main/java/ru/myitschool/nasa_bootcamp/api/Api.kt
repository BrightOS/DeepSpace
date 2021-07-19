package com.example.kotlintraining.api

import com.example.firstkotlinapp.model.Rovers
import com.example.kotlintraining.model.Apod
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("planetary/apod?api_key=uej4DeQlgTn9GRLfb98qSj38c2mecIuWspj3JyTN")
    suspend fun getAstronomyImageOfTheDay2(): Response<Apod> // Изображение дня от НАСА

//    @GET("planetary/apod?api_key=uej4DeQlgTn9GRLfb98qSj38c2mecIuWspj3JyTN")
//    fun getAstronomyImageOfTheDay(): Call<Apod?>?

    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=2&api_key=uej4DeQlgTn9GRLfb98qSj38c2mecIuWspj3JyTN")
    suspend fun getRoverPhotos() : Response<Rovers> //Фотографии роверов с Марса
}