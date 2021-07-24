package ru.myitschool.nasa_bootcamp.ui.auth.reg

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.utils.Data
import java.lang.Exception

class RegViewModelImpl : ViewModel(), RegViewModel {
    private var authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private var userDbReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("user_data")
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    override suspend fun createUser(
        userName: String,
        email: String,
        password: String,
        imagePath: Uri?
    ): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            val user = authenticator.createUserWithEmailAndPassword(email, password).await()
            if (user != null) {
                userDbReference.child(user.user!!.uid).child("username").setValue(userName).await()
                val storageRef = storage.getReference("user_data/${user.user?.uid}")
                if (imagePath != null) {
                    storageRef.putFile(imagePath).await()
                }
                returnData.postValue(Data.Ok(user.user!!))
            } else {
                returnData.postValue(Data.Error("Unknown error happened."))
            }
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }
}