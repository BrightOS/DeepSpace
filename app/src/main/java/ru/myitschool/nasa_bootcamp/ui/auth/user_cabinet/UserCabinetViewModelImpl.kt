package ru.myitschool.nasa_bootcamp.ui.auth.user_cabinet

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

/*
 * @author Vladimir Abubakirov
 */
class UserCabinetViewModelImpl : ViewModel(), UserCabinetViewModel {
    private var authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private var userDbReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("user_data")

    override suspend fun changeEmail(newEmail: String) {
        authenticator.currentUser?.updateEmail(newEmail)?.await()
    }

    override suspend fun changeNickName(newNick: String) {
        userDbReference.child(authenticator.uid!!).setValue(newNick).await()
    }

    override suspend fun changePassword(newPass: String) {
        authenticator.currentUser?.updatePassword(newPass)?.await()
    }
}