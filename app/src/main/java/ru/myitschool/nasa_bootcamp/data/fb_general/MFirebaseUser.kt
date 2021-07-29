package ru.myitschool.nasa_bootcamp.data.fb_general

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.utils.Data
import java.io.File
import java.lang.Exception

class MFirebaseUser() : ViewModel() {
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    fun isUserAuthenticated(): Boolean {
        return authenticator.currentUser != null
    }
 

    fun isVerified(): Boolean {
        return authenticator.currentUser!!.isEmailVerified
    }

    fun getUser(): FirebaseUser {
        return authenticator.currentUser!!
    }

    suspend fun sendConfirmationEmail() {
        try {
            authenticator.currentUser!!.sendEmailVerification().await()
            println("Sent" + authenticator.currentUser!!.email)
        }
        catch(e: Exception) {
            println(e.message.toString())
        }
    }

    suspend fun getUserAvatar(): LiveData<Data<out Bitmap>> {
        val returnData: MutableLiveData<Data<out Bitmap>> = MutableLiveData()
        val storageRef = storage.getReference("user_data/${authenticator.currentUser?.uid}")
        try {
            var tempLocalFile: File? = null
            kotlin.runCatching {
                tempLocalFile = File.createTempFile("Images", "bmp")
            }
            storageRef.getFile(tempLocalFile!!).addOnSuccessListener {
                returnData.postValue(Data.Ok(BitmapFactory.decodeFile(tempLocalFile!!.absolutePath)))
            }.addOnFailureListener {
                returnData.postValue(Data.Error(it.message.toString()))
            }.await()
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    fun signOutUser(): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            authenticator.signOut()
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }
}