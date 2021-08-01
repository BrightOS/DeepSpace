package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), SpaceXViewModel {

    private var launchesModelsList: MutableLiveData<List<SxLaunchModel>> =
        MutableLiveData<List<SxLaunchModel>>()

    private var errorHandler: MutableLiveData<Status> = MutableLiveData<Status>(
        Status.SUCCESS
    )


    var list: List<SxLaunchModel> = listOf()

    override suspend fun loadSpaceXLaunches() {
        errorHandler.postValue(Status.LOADING)
        val response = repository.getSpaceXLaunches()

        if (response.isSuccessful) {
            if (response.body() != null) {
                launchesModelsList.postValue(
                    response.body()!!.map { launch -> launch.createLaunchModel() }.asReversed()
                )
                errorHandler.postValue(Status.SUCCESS)
            }
        } else {
            Log.e(
                "SPACEX_LAUNCH_VIEWMODEL_TAG",
                "Error occured while trying to upload files from launches api"
            )
            errorHandler.postValue(Status.ERROR)
        }
    }

    override fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>> {
        return launchesModelsList
    }

    override fun getViewModelScope() = viewModelScope
    override fun getErrorHandler(): MutableLiveData<Status> {
        return errorHandler
    }

}