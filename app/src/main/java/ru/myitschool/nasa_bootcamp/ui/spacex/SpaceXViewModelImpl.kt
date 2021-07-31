package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.SpaceXRepository
import ru.myitschool.nasa_bootcamp.utils.ErrorHandler
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), SpaceXViewModel {

    private var launchesModelsList: MutableLiveData<List<SxLaunchModel>> =
        MutableLiveData<List<SxLaunchModel>>()

    private var errorHandler: MutableLiveData<ErrorHandler> = MutableLiveData<ErrorHandler>(
        ErrorHandler.SUCCESS
    )


    var list: List<SxLaunchModel> = listOf()

    override suspend fun loadSpaceXLaunches() {
        errorHandler.postValue(ErrorHandler.LOADING)
        val response = repository.getSpaceXLaunches()

        if (response.isSuccessful) {
            if (response.body() != null) {
                launchesModelsList.postValue(
                    response.body()!!.map { launch -> launch.createLaunchModel() }.asReversed()
                )
                errorHandler.postValue(ErrorHandler.SUCCESS)
            }
        } else {
            Log.e(
                "SPACEX_LAUNCH_VIEWMODEL_TAG",
                "Error occured while trying to upload files from launches api"
            )
            errorHandler.postValue(ErrorHandler.ERROR)
        }
    }

    override fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>> {
        return launchesModelsList
    }

    override fun getViewModelScope() = viewModelScope
    override fun getErrorHandler(): MutableLiveData<ErrorHandler> {
        return errorHandler
    }

}