package ru.myitschool.nasa_bootcamp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.wrongCredits
import java.io.IOException
import java.lang.Exception

class AuthViewModelImpl : ViewModel(), AuthViewModel {
    private var authenticator: FirebaseAuth? = FirebaseAuth.getInstance()

    override suspend fun authenticateUser(
        email: String,
        password: String
    ): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            val user = authenticator?.signInWithEmailAndPassword(email, password)?.await()
            returnData.postValue(Data.Ok(user?.user!!))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override fun signOutUser(): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            authenticator?.signOut()
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }
}