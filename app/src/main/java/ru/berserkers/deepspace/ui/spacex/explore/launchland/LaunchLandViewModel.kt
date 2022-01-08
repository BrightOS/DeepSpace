package ru.berserkers.deepspace.ui.spacex.explore.launchland

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.LandPadModel
import ru.berserkers.deepspace.data.model.LaunchPadModel
import ru.berserkers.deepspace.utils.Status

interface LaunchLandViewModel {
    suspend fun getLaunchPads()
    suspend fun getLandPads()
    fun getViewModelScope(): CoroutineScope
    fun getLandList(): MutableLiveData<ArrayList<LandPadModel>>
    fun getLaunchList(): MutableLiveData<ArrayList<LaunchPadModel>>
    fun getStatus(): MutableLiveData<Status>
}