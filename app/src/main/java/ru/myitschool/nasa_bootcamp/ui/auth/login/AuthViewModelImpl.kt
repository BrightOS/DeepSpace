package ru.myitschool.nasa_bootcamp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ru.myitschool.nasa_bootcamp.utils.Data

class AuthViewModelImpl : ViewModel(), AuthViewModel {

    override fun loginUser(email: String, password: String): LiveData<Data<out FirebaseUser>> {
        return MutableLiveData()
    }
}