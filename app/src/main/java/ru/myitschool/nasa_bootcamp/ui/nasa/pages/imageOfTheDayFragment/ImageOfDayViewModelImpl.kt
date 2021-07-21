package ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.MainRepository
import javax.inject.Inject

@HiltViewModel
class ImageOfDayViewModelImpl @Inject constructor(
    private val nasaRepo : MainRepository
) : ViewModel(), ImageOfDayViewModel {

    suspend fun loadImageOfDay(){

        val response = nasaRepo.getImageOfTheDay()
        print(response.body()!!.title)
    }
}