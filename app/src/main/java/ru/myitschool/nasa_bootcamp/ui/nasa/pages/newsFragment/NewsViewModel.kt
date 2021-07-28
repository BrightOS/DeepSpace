package ru.myitschool.nasa_bootcamp.ui.nasa.pages.newsFragment

import androidx.lifecycle.ViewModel

interface NewsViewModel {
    suspend fun getNews()
}