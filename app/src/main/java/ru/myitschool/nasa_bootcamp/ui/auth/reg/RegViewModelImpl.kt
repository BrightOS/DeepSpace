package ru.myitschool.nasa_bootcamp.ui.auth.reg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ru.myitschool.nasa_bootcamp.utils.Data

class RegViewModelImpl : ViewModel(), RegViewModel {
    override fun register(
        userName: String,
        email: String,
        password: String
    ): LiveData<Data<out FirebaseUser>> {
        return MutableLiveData()
    }
}