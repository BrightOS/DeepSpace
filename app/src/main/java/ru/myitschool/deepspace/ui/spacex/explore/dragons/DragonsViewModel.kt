package ru.myitschool.deepspace.ui.spacex.explore.dragons

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.DragonModel
import ru.myitschool.deepspace.utils.Status

interface DragonsViewModel {
    suspend fun getDragons()
    fun getViewModelScope(): CoroutineScope
    fun getDragonsList(): MutableLiveData<ArrayList<DragonModel>>
    fun getStatus(): MutableLiveData<Status>
}
