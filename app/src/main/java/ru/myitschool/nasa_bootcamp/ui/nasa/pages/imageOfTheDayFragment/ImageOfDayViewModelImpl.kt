package ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import javax.inject.Inject

@HiltViewModel
class ImageOfDayViewModelImpl @Inject constructor(
    private val nasaRepo : NasaRepository
) : ViewModel(), ImageOfDayViewModel {

    suspend fun loadImageOfDay(){
        val response = nasaRepo.getImageOfTheDay()
        Log.d("TAGTAG_A", response.body()!!.url)
    }
}