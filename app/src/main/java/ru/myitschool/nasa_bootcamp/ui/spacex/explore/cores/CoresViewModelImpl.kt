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
import javax.inject.Inject

@HiltViewModel
class CoresViewModelImpl  @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), CoresViewModel {

    var capsuleModels: MutableLiveData<ArrayList<CoreModel>> =
    MutableLiveData<ArrayList<CoreModel>>()

    var list: ArrayList<CoreModel> = arrayListOf()

    override suspend fun getCores() {
        val response = repository.getCores()

        for (core in response.body()!!) {
            list.add(core.createCoreModel())
        }
        capsuleModels.value = list
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getCoresList(): MutableLiveData<ArrayList<CoreModel>> = capsuleModels


}