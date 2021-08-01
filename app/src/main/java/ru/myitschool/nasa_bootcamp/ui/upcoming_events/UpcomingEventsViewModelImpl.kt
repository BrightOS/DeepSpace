package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.data.repository.UpcomingRepository
import javax.inject.Inject

@HiltViewModel
class UpcomingEventsViewModelImpl @Inject constructor(
    private val repository: UpcomingRepository
) : ViewModel(), UpcomingEventsViewModel {

    private var upcomingList: MutableLiveData<ArrayList<UpcomingLaunchModel>> =
        MutableLiveData<ArrayList<UpcomingLaunchModel>>()

    private var list: ArrayList<UpcomingLaunchModel> = arrayListOf()

    override fun getUpcomingList(): MutableLiveData<ArrayList<UpcomingLaunchModel>> = upcomingList

    override suspend fun getUpcomingLaunches() {
        val response = repository.getUpcomingLaunches()

        for (launch in response.body()!!) {
            Log.d(
                "UPCOMING_EVENT_LOAD_TAG",
                " Is upcoming? ${launch.createUpcomingLaunchModel().upcoming}"
            )
            if (launch.createUpcomingLaunchModel().upcoming!!) {
                list.add(launch.createUpcomingLaunchModel())
            }
        }

        upcomingList.value = list
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}