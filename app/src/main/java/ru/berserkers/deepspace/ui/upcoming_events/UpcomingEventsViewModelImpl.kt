package ru.berserkers.deepspace.ui.upcoming_events

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.UpcomingLaunchModel
import ru.berserkers.deepspace.data.repository.UpcomingRepository
import javax.inject.Inject

/*
 * @author Samuil Nalisin
 */
@HiltViewModel
class UpcomingEventsViewModelImpl @Inject constructor(
    private val repository: UpcomingRepository
) : ViewModel(), UpcomingEventsViewModel {

    private var upcomingList: MutableLiveData<ArrayList<UpcomingLaunchModel>> =
        MutableLiveData<ArrayList<UpcomingLaunchModel>>(arrayListOf())

    private var list: ArrayList<UpcomingLaunchModel> = arrayListOf()

    override fun getUpcomingList(): MutableLiveData<ArrayList<UpcomingLaunchModel>> = upcomingList

    override suspend fun getUpcomingLaunches() {
        try {
        val response = repository.getUpcomingLaunches()

        for (launch in response.body()!!)
            if (launch.createUpcomingLaunchModel().upcoming!!)
                list.add(launch.createUpcomingLaunchModel())

        upcomingList.value = list

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}
