package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), SpaceXViewModel {

    private var launchesModelsList: MutableLiveData<List<SxLaunchModel>> =
        MutableLiveData<List<SxLaunchModel>>()


    var list: List<SxLaunchModel> = listOf()

    override suspend fun loadSpaceXLaunches() {
        val response = repository.getSpaceXLaunches()

        if (response.isSuccessful) {
            if (response.body() != null) {
                launchesModelsList.postValue(
                    response.body()!!.map { launch -> launch.createLaunchModel() }.asReversed()
                )
            }
        } else {

        }
    }

    override fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>> {
        return launchesModelsList
    }

    override fun getViewModelScope() = viewModelScope

}