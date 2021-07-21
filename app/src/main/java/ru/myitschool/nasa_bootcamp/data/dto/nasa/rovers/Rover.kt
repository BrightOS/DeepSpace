package com.example.firstkotlinapp.model.rovers

import com.google.gson.annotations.SerializedName

class Rover(
    @field:SerializedName("id") private val id: Int,
    @field:SerializedName("name") val name: String, //Название ровера
    @field:SerializedName("landing_date") private val landing_date: String, //Дата прибытия на Марс
    @field:SerializedName("launch_date") private val launch_date: String, //Дата отправки с Земли
    @field:SerializedName("status") private val status: String //Активен сейчас или нет
)