package ru.myitschool.nasa_bootcamp.ui.auth.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import ru.myitschool.nasa_bootcamp.utils.Data



interface AuthViewModel {
    /**
     * если вход успешный то вернется Data.Ok, какого будет он типа разберемся потом, нужен ли будет
     * в интерфейсе FirebaseUser или нет
     *
     * если вход с неправильными данными, пароль или почта то должен вернуться Data.Error с
     * message = Constants.wrongCredits
     */
    fun loginUser(email: String, password: String): LiveData<Data<out FirebaseUser>>
}