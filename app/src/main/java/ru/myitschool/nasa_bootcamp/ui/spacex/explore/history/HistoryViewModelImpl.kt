package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), HistoryViewModel{
    var historyModels: MutableLiveData<ArrayList<HistoryModel>> = MutableLiveData<ArrayList<HistoryModel>>()
    var list: ArrayList<HistoryModel> = arrayListOf()

    override suspend fun getHistory() {
        val response = repository.getHistory()

        for (story in response.body()!!) {
            list.add(story.createHistoryModel())
        }
        historyModels.value = list
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>> {
        return historyModels
    }


}