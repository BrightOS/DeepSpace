package ru.berserkers.deepspace.ui.spacex.explore.launchland

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.LandPadModel
import ru.berserkers.deepspace.data.model.LaunchPadModel
import ru.berserkers.deepspace.data.repository.SpaceXRepository
import ru.berserkers.deepspace.utils.Status
import javax.inject.Inject

@HiltViewModel
class LaunchLandViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), LaunchLandViewModel {

    private var landModels: MutableLiveData<ArrayList<LandPadModel>> = MutableLiveData<ArrayList<LandPadModel>>()
    private var listLand: ArrayList<LandPadModel> = arrayListOf()
    private var status: MutableLiveData<Status> = MutableLiveData<Status>()
    private var launchModels: MutableLiveData<ArrayList<LaunchPadModel>> = MutableLiveData<ArrayList<LaunchPadModel>>()
    private var listLaunch: ArrayList<LaunchPadModel> = arrayListOf()


    override suspend fun getLaunchPads() {
        val response = repository.getLaunchPads()

        for (launch in response.body()!!) {
            listLaunch.add(launch.createLaunchPadModel())
            Log.d("LAUNCH_TAG", launch.createLaunchPadModel().name)
        }
        launchModels.value = listLaunch
    }

    override suspend fun getLandPads() {
        status.value = Status.LOADING
        val response = repository.getLandPads()

        if(response.isSuccessful) {
            status.value = Status.SUCCESS
            for (land in response.body()!!) {
                listLand.add(land.createLandPadModel())
                Log.d(
                    "LAND_TAG",
                    "Name: ${land.createLandPadModel().full_name} Lat: ${land.createLandPadModel().location.latitude} Lan : ${land.createLandPadModel().location.longitude}"
                )
            }
            landModels.value = listLand
        }else{
            status.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getLandList(): MutableLiveData<ArrayList<LandPadModel>>  = landModels

    override fun getLaunchList(): MutableLiveData<ArrayList<LaunchPadModel>> = launchModels

    override fun getStatus(): MutableLiveData<Status> {
        return status
    }


}