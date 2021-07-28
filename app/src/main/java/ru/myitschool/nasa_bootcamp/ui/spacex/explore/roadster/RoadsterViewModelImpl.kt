package ru.myitschool.nasa_bootcamp.ui.spacex.explore.roadster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.LaunchPadModel
import ru.myitschool.nasa_bootcamp.data.model.RoadsterModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland.LaunchLandViewModel
import javax.inject.Inject

@HiltViewModel
class RoadsterViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), RoadsterViewModel {

    var roadsterModell: MutableLiveData<RoadsterModel> = MutableLiveData<RoadsterModel>()


    override suspend fun getRoadsterInfo() {
        val response = repository.getRoadster()

        roadsterModell.value = response.body()!!.createRoadsterModel()
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getRoadsterModel(): MutableLiveData<RoadsterModel> {
        return roadsterModell
    }


}