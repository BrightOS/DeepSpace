package ru.berserkers.deepspace.ui.spacex.explore.cores

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.CoreModel
import ru.berserkers.deepspace.utils.Status

interface CoresViewModel {
    suspend fun getCores()
    fun getViewModelScope(): CoroutineScope
    fun getCoresList(): MutableLiveData<ArrayList<CoreModel>>
    fun getStatus(): MutableLiveData<Status>
}
