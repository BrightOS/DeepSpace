package ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.DragonModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

@HiltViewModel
class DragonsViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), DragonsViewModel {

    private var status : MutableLiveData<Status> = MutableLiveData<Status>()

    var dragonModels: MutableLiveData<ArrayList<DragonModel>> =
        MutableLiveData<ArrayList<DragonModel>>()

    var list: ArrayList<DragonModel> = arrayListOf()

    override suspend fun getDragons() {
        status.value = Status.LOADING
        val response = repository.getDragons()

        if (response.isSuccessful) {
            status.value = Status.SUCCESS
            for (dragon in response.body()!!) {
                list.add(dragon.createDragonModel())
            }
            dragonModels.value = list
        } else {
            status.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getDragonsList(): MutableLiveData<ArrayList<DragonModel>> {
        return dragonModels
    }

    override fun getStatus(): MutableLiveData<Status> {
        return status
    }
}
