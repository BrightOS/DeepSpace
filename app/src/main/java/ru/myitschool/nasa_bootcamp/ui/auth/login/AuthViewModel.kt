package ru.myitschool.nasa_bootcamp.ui.auth.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.utils.Data



interface AuthViewModel {
    /**
     * если вход успешный то вернется Data.Ok, какого будет он типа разберемся потом, нужен ли будет
     * в интерфейсе FirebaseUser или нет
     *
     * если вход с неправильными данными, пароль или почта то должен вернуться Data.Error с
     * message = Constants.wrongCredits
     */
    suspend fun authenticateUser(email: String, password: String): LiveData<Data<out FirebaseUser>>
    fun signOutUser(): LiveData<Data<out String>>
    fun getViewModelScope(): CoroutineScope
}