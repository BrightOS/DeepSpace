package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXLaunchRepository
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(private val repository : SpaceXLaunchRepository
): ViewModel(), SpaceXViewModel {

    var launchesModelsList: MutableLiveData<ArrayList<SxLaunchModel>> = MutableLiveData<ArrayList<SxLaunchModel>>()


    var list: ArrayList<SxLaunchModel> = arrayListOf()

    override suspend fun getSpaceXLaunches(){

        val response = repository.getSpaceXLaunches()

        if (response.isSuccessful) {
            if (response.body() != null) {

                var i : Int = 0
                for (launch in response.body()!!) {
                    Log.d("TAG_SPACEX", launch.createLaunchModel().mission_name)
                    list.add(launch.createLaunchModel())
                    i++
                }
            }
        }else{

        }
        list.reverse()
        launchesModelsList.value = list
    }

    override fun getLaunchesList(): MutableLiveData<ArrayList<SxLaunchModel>> {
        return  launchesModelsList
    }

    override fun getViewModelScope() = viewModelScope

}