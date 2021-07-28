package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.data.model.LandPadModel
import ru.myitschool.nasa_bootcamp.data.model.LaunchPadModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.history.HistoryViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchLandViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), LaunchLandViewModel {

    var landModels: MutableLiveData<ArrayList<LandPadModel>> = MutableLiveData<ArrayList<LandPadModel>>()
    var listLand: ArrayList<LandPadModel> = arrayListOf()

    var launchModels: MutableLiveData<ArrayList<LaunchPadModel>> = MutableLiveData<ArrayList<LaunchPadModel>>()
    var listLaunch: ArrayList<LaunchPadModel> = arrayListOf()


    override suspend fun getLaunchLaunchPads() {
        val response = repository.getLaunchPads()

        for (launch in response.body()!!) {
            listLaunch.add(launch.createLaunchPadModel())
        }
        launchModels.value = listLaunch
    }

    override suspend fun getLandPads() {
        val response = repository.getLandPads()

        for (land in response.body()!!) {
            listLand.add(land.createLandPadModel())
        }
        landModels.value = listLand
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getLandList(): MutableLiveData<ArrayList<LandPadModel>>  = landModels

    override fun getLaunchList(): MutableLiveData<ArrayList<LaunchPadModel>> = launchModels


}