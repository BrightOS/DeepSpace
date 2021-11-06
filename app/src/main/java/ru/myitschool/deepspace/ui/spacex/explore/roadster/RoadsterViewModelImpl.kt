package ru.myitschool.deepspace.ui.spacex.explore.roadster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.RoadsterModel
import ru.myitschool.deepspace.data.repository.SpaceXRepository
import ru.myitschool.deepspace.utils.Status
import javax.inject.Inject

@HiltViewModel
class RoadsterViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), RoadsterViewModel {

    private var roadsterModell: MutableLiveData<RoadsterModel> = MutableLiveData<RoadsterModel>()
    private var status: MutableLiveData<Status> = MutableLiveData<Status>()

    override suspend fun getRoadsterInfo() {
        status.value = Status.LOADING
        val response = repository.getRoadster()

        if(response.isSuccessful) {
            status.value = Status.SUCCESS
            roadsterModell.value = response.body()!!.createRoadsterModel()
        }else{
            status.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getRoadsterModel(): MutableLiveData<RoadsterModel> {
        return roadsterModell
    }

    override fun getStatus(): MutableLiveData<Status> {
        return status
    }
}
