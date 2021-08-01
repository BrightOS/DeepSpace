package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.myitschool.nasa_bootcamp.utils.Status


class LaunchItemViewModel : ViewModel() {
    private val isLoaded = MutableLiveData(false)
    var error: MutableLiveData<Status> = MutableLiveData<Status>(Status.SUCCESS)

    fun setError(handler: Status) {
        error.value = handler
    }

    fun isViewModelLoaded(): LiveData<Boolean> {
        return isLoaded
    }

    fun setIsGifLoaded(isLoaded: Boolean) {
        this.isLoaded.value = isLoaded
    }
}