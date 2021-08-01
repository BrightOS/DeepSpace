package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel

interface UpcomingEventsViewModel  {
    suspend fun getUpcomingLaunches()
    fun getViewModelScope(): CoroutineScope
    fun getUpcomingList(): MutableLiveData<ArrayList<UpcomingLaunchModel>>
}