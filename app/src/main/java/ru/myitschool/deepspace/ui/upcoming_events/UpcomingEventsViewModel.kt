package ru.myitschool.deepspace.ui.upcoming_events

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.UpcomingLaunchModel

interface UpcomingEventsViewModel  {
    suspend fun getUpcomingLaunches()
    fun getViewModelScope(): CoroutineScope
    fun getUpcomingList(): MutableLiveData<ArrayList<UpcomingLaunchModel>>
}
