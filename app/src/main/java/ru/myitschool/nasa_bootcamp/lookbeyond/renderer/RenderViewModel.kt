package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RenderViewModel : ViewModel() {
    val isLoaded : MutableLiveData<Boolean> = MutableLiveData()
}