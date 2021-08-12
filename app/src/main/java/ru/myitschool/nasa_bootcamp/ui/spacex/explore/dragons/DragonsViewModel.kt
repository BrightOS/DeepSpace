package ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.DragonModel
import ru.myitschool.nasa_bootcamp.utils.Status

interface DragonsViewModel {
    suspend fun getDragons()
    fun getViewModelScope(): CoroutineScope
    fun getDragonsList(): MutableLiveData<ArrayList<DragonModel>>
    fun getStatus(): MutableLiveData<Status>
}