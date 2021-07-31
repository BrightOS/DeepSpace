package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.utils.ErrorHandler
import javax.inject.Inject


class LaunchItemViewModel : ViewModel() {
    private val isLoaded = MutableLiveData(false)
    var error: MutableLiveData<ErrorHandler> = MutableLiveData<ErrorHandler>(ErrorHandler.SUCCESS)

    fun setError(handler: ErrorHandler) {
        error.value = handler
    }

    fun isViewModelLoaded(): LiveData<Boolean> {
        return isLoaded
    }

    fun setIsGifLoaded(isLoaded: Boolean) {
        this.isLoaded.value = isLoaded
    }
}