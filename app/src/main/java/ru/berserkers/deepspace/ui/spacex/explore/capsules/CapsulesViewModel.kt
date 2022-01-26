package ru.berserkers.deepspace.ui.spacex.explore.capsules

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.CapsuleModel
import ru.berserkers.deepspace.utils.Status

interface CapsulesViewModel {
    suspend fun getCapsules()
    fun getViewModelScope(): CoroutineScope
    fun getCapsulesList(): MutableLiveData<ArrayList<CapsuleModel>>
    fun getStatus(): MutableLiveData<Status>
}
