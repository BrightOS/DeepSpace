package ru.myitschool.nasa_bootcamp.ui.nasa.pages.newsFragment

import androidx.lifecycle.ViewModel
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import ru.myitschool.nasa_bootcamp.data.repository.NewsRepository
import ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment.ImageOfDayViewModel
import javax.inject.Inject

class NewsViewModelIml @Inject constructor(
    private val newsRepos : NewsRepository
) : ViewModel(), NewsViewModel {
    override suspend fun getNews() {
        
    }

}