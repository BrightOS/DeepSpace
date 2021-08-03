package ru.myitschool.nasa_bootcamp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import javax.inject.Inject

@HiltViewModel
class AuthViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), AuthViewModel {
    override suspend fun authenticateUser(
        email: String,
        password: String
    ): LiveData<Data<out FirebaseUser>> {
        return repository.authenticateUser(email, password)
    }

    override fun signOutUser(): LiveData<Data<out String>> {
        return repository.signOutUser()
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}