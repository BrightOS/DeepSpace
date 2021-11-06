package ru.myitschool.deepspace.ui.spacex.explore.launchland

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.LandPadModel
import ru.myitschool.deepspace.data.model.LaunchPadModel
import ru.myitschool.deepspace.utils.Status

interface LaunchLandViewModel {
    suspend fun getLaunchPads()
    suspend fun getLandPads()
    fun getViewModelScope(): CoroutineScope
    fun getLandList(): MutableLiveData<ArrayList<LandPadModel>>
    fun getLaunchList(): MutableLiveData<ArrayList<LaunchPadModel>>
    fun getStatus(): MutableLiveData<Status>
}