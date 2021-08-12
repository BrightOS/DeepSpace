package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules.CapsulesViewModel
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

@HiltViewModel
class CoresViewModelImpl  @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), CoresViewModel {

    var capsuleModels: MutableLiveData<ArrayList<CoreModel>> =
    MutableLiveData<ArrayList<CoreModel>>()

    private var status : MutableLiveData<Status> = MutableLiveData<Status>()

    var list: ArrayList<CoreModel> = arrayListOf()

    override suspend fun getCores() {
        status.value = Status.LOADING
        val response = repository.getCores()

        if (response.isSuccessful) {
            status.value = Status.SUCCESS
            for (core in response.body()!!) {
                list.add(core.createCoreModel())
            }
            capsuleModels.value = list
        }else{
            status.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getCoresList(): MutableLiveData<ArrayList<CoreModel>> = capsuleModels

    override fun getStatus(): MutableLiveData<Status> {
        return status
    }


}