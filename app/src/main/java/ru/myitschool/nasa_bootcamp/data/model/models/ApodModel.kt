package com.example.firstkotlinapp.model.models

data class ApodModel(
    val copyright: String,
    val date: String, //Дата
    val explanation: String, //Описание
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String, //Заголовок
    val url: String, //Ссылка на изображение
)
