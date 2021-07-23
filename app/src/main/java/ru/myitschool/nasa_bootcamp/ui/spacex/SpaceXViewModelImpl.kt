package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.repository.ImageOfDayRepository
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXLaunchRepository
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(private val repository : SpaceXLaunchRepository
): ViewModel(), SpaceXViewModel {

    suspend fun getSpaceXLaunches(){

        val response = repository.getSpaceXLaunches()
        Log.d("TAG_SPACEX", response.body()!!.get(0).mission_name)
    }
}