package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.ImageOfDayRepository
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXLaunchRepository
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(private val repository : SpaceXLaunchRepository
): ViewModel(), SpaceXViewModel {

    var launchesModelsList: MutableLiveData<ArrayList<SxLaunchModel>> = MutableLiveData<ArrayList<SxLaunchModel>>()


    var list: ArrayList<SxLaunchModel> = arrayListOf()

    suspend fun getSpaceXLaunches(){

        val response = repository.getSpaceXLaunches()
        Log.d("TAG_SPACEX", response.body()!!.get(0).mission_name)

        if (response.isSuccessful) {
            if (response.body() != null) {
                for (launch in response.body()!!) {
                    list.add(launch.createLaunchModel())
                }
            }
        }

    }


}