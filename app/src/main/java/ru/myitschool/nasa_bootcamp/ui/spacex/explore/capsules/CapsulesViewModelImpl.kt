package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.capsules.Capsule
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModel
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

@HiltViewModel
class CapsulesViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), CapsulesViewModel {
    private val errorHandler: MutableLiveData<Status> = MutableLiveData<Status>()
    var capsuleModels: MutableLiveData<ArrayList<CapsuleModel>> =
        MutableLiveData<ArrayList<CapsuleModel>>()

    var list: ArrayList<CapsuleModel> = arrayListOf()

    override suspend fun getCapsules() {
        errorHandler.value = Status.LOADING
        val response = repository.getCapsules()

        if (response.isSuccessful) {
            errorHandler.value = Status.SUCCESS
            for (capsule in response.body()!!) {
                list.add(capsule.createCapsuleModel())
            }
            capsuleModels.value = list
        } else {
            errorHandler.value = Status.ERROR
        }
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getCapsulesList(): MutableLiveData<ArrayList<CapsuleModel>> {
        return capsuleModels
    }


}