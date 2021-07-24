package ru.myitschool.nasa_bootcamp.ui.registration

import android.graphics.Bitmap
import android.net.Uri
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class RegistrationViewModelImpl: ViewModel(), RegistrationViewModel {
    private var authenticator: FirebaseAuth? = null
    private var reference: DatabaseReference
    private var storage: FirebaseStorage
    var isSuccess: Boolean
    var error: String

    init {
        authenticator = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference("user_data")
        storage = FirebaseStorage.getInstance()
        isSuccess = true
        error = ""
    }

    override suspend fun createUser(email: String, password: String,  username: String, imagePath: Uri?) {
        try {
            //ContactsContract.Contacts.Data<FirebaseUser>
            val user = authenticator?.createUserWithEmailAndPassword(email, password)?.await()
            if (user != null) {
                reference.child(user.user!!.uid).child("username").setValue(username).await()
                val storageRef = storage.getReference("user_data/${user.user?.uid}")
                if (imagePath != null) {
                    storageRef.putFile(imagePath).await()
                }
            }
            else {
                isSuccess = false
            }
        }
        catch (e: Exception){
            isSuccess = false
            error = e.message.toString()
        }
    }

    override suspend fun authenticateUser(email: String, password: String) {
        try {
            authenticator?.signInWithEmailAndPassword(email, password)?.await()
            isSuccess = true
        }
        catch (e: Exception){
            isSuccess = false
            error = e.message.toString()
        }
    }

    override suspend fun signOutUser() {
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