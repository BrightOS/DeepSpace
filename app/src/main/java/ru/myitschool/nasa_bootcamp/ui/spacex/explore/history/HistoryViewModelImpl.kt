package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

@HiltViewModel
class HistoryViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), HistoryViewModel {
    private var status: MutableLiveData<Status> = MutableLiveData<Status>()

    override fun getStatus(): MutableLiveData<Status> {
        return status
    }

    private var historyModels: MutableLiveData<ArrayList<HistoryModel>> =
        MutableLiveData<ArrayList<HistoryModel>>()
    var list: ArrayList<HistoryModel> = arrayListOf()

    override suspend fun getHistory() {
        status.value = Status.LOADING
        val response = repository.getHistory()

        if (response.isSuccessful) {
            status.value = Status.SUCCESS
            for (story in response.body()!!) {
                list.add(story.createHistoryModel())
            }
            historyModels.value = list
        } else {
            status.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>> {
        return historyModels
    }
}