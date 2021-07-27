package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

interface SpaceXViewModel {
    suspend fun getSpaceXLaunches()
    fun getLaunchesList(): MutableLiveData<ArrayList<SxLaunchModel>>
}