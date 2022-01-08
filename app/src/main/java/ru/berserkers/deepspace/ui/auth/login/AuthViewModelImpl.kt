package ru.berserkers.deepspace.ui.auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.repository.FirebaseRepository
import ru.berserkers.deepspace.utils.Data
import javax.inject.Inject

/*
 * @author Vladimir Abubakirov
 */
@HiltViewModel
class AuthViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), AuthViewModel {
    override suspend fun authenticateUser(
        context: Context,
        email: String,
        password: String
    ): Data<FirebaseUser> {
        return repository.authenticateUser(context, email, password)
    }

    override fun signOutUser(context: Context): LiveData<Data<out String>> {
        return repository.signOutUser(context)
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}
