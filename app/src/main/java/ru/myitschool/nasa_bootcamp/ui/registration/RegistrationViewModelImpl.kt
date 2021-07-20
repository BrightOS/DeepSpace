package ru.myitschool.nasa_bootcamp.ui.registration

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class RegistrationViewModelImpl: ViewModel(), RegistrationViewModel {
    private var authenticator: FirebaseAuth? = null
    private var reference: DatabaseReference
    var isSuccess: Boolean
    var error: String

    init {
        authenticator = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference("user_data")
        isSuccess = true
        error = ""
    }

    override suspend fun CreateUser(email: String, password: String) {
        try {
            authenticator?.createUserWithEmailAndPassword(email, password)?.await()
            isSuccess = true
        }
        catch (e: Exception){
            isSuccess = false
            error = e.message.toString()
        }
    }

    override suspend fun AuthenticateUser(email: String, password: String) {
        try {
            authenticator?.signInWithEmailAndPassword(email, password)?.await()
            isSuccess = true
        }
        catch (e: Exception){
            isSuccess = false
            error = e.message.toString()
        }
    }

    override suspend fun SignOutUser() {
        try {
            authenticator?.signOut()
            isSuccess = true
        }
        catch (e: Exception) {
            isSuccess = false
            error = e.message.toString()
        }
    }
}