package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.capsules.Capsule
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.utils.Status

interface CapsulesViewModel {
    suspend fun getCapsules()
    fun getViewModelScope(): CoroutineScope
    fun getCapsulesList(): MutableLiveData<ArrayList<CapsuleModel>>
    fun getStatus(): MutableLiveData<Status>
}