package ru.myitschool.deepspace.ui.spacex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myitschool.deepspace.data.model.SxLaunchModel
import ru.myitschool.deepspace.data.repository.SpaceXRepository
import ru.myitschool.deepspace.data.repository.UpcomingRepository
import ru.myitschool.deepspace.data.room.LaunchesDao
import ru.myitschool.deepspace.utils.Data
import ru.myitschool.deepspace.utils.Status
import javax.inject.Inject

/*
 * @author Samuil Nalisin
 */
@HiltViewModel
class SpaceXViewModelImpl @Inject constructor(
    private val repository: SpaceXRepository,
    private val launchesDao: LaunchesDao,
    private val upcomingRepository: UpcomingRepository,
) : ViewModel(), SpaceXViewModel {

    private var errorHandler: MutableLiveData<Status> = MutableLiveData<Status>(Status.LOADING)
    private val liveData = MutableLiveData<Data<List<SxLaunchModel>>>(Data.Loading)

    override suspend fun reconnectToSpaceX() {
        val response = repository.getSpaceXLaunches()
        if (response.body() != null) {
            val launches = response.body()!!
            val sxLaunches = launches.map { launch -> launch.createLaunchModel() }.asReversed()
            liveData.postValue(Data.Ok(sxLaunches))
            errorHandler.postValue(Status.SUCCESS)
            Log.i("vm_debug", "retrofit got")
            //launchesDao.insertAllLaunches(launches)
            Log.i("vm_debug", "launches saved")

        }
    }

    override fun getSpaceXLaunches(): LiveData<Data<List<SxLaunchModel>>> {
        viewModelScope.launch(Dispatchers.IO) {
            val launches = launchesDao.getAllLaunches().map { launch -> launch.createLaunchModel() }
                .asReversed()
            if (liveData.value !is Data.Ok) {
                liveData.postValue(Data.Local(launches))
                errorHandler.postValue(Status.SUCCESS)
                Log.i("vm_debug", "room got")
            }
        }
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val response = repository.getSpaceXLaunches()

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val launches = response.body()!!
                        val sxLaunches =
                            launches.map { launch -> launch.createLaunchModel() }.asReversed()
                        liveData.postValue(Data.Ok(sxLaunches))
                        errorHandler.postValue(Status.SUCCESS)
                        Log.i("vm_debug", "retrofit got")
                        //launchesDao.insertAllLaunches(launches)
                        Log.i("vm_debug", "launches saved")

                    }
                }
            } catch (e: Exception) {
                liveData.postValue(Data.Error("noInternet"))
            }
        }
        return liveData
    }

    override fun getErrorHandler(): MutableLiveData<Status> {
        return errorHandler
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}
