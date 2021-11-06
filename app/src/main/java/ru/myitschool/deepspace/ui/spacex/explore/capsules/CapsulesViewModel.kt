package ru.myitschool.deepspace.ui.spacex.explore.capsules

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.CapsuleModel
import ru.myitschool.deepspace.utils.Status

interface CapsulesViewModel {
    suspend fun getCapsules()
    fun getViewModelScope(): CoroutineScope
    fun getCapsulesList(): MutableLiveData<ArrayList<CapsuleModel>>
    fun getStatus(): MutableLiveData<Status>
}