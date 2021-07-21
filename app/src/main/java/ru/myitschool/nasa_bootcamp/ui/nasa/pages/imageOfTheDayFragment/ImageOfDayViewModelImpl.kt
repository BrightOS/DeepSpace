package ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.MainModule
import ru.myitschool.nasa_bootcamp.data.MainRepository
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.ui.registration.RegistrationViewModel
import javax.inject.Inject

@HiltViewModel
class ImageOfDayViewModelImpl @Inject constructor(
    private val nasaRepo : MainRepository
) : ViewModel(), ImageOfDayViewModel {

    suspend fun loadImageOfDay(){

        val response = nasaRepo.getImageOfDay()
        print(response.body()!!.title)
    }
}