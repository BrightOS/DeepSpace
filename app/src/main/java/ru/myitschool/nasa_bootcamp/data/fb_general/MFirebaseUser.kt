package ru.myitschool.nasa_bootcamp.data.fb_general

import android.content.Context
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
import ru.myitschool.nasa_bootcamp.utils.downloadFirebaseImage
import java.io.File
import java.lang.Exception

class MFirebaseUser() : ViewModel() {
    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    private val sharedPreferencesFileName = "currentUser"

    fun isUserAuthenticated(): Boolean {
        return authenticator.currentUser != null
    }

    suspend fun getUserAvatar(): Data<Bitmap> {
        val storageRef = storage.getReference("user_data/${authenticator.currentUser?.uid}")
        return downloadFirebaseImage(storageRef)
    }

    fun signOutUser(context: Context): LiveData<Data<FirebaseUser>> {
        val returnData: MutableLiveData<Data<FirebaseUser>> = MutableLiveData()
        try {
            authenticator.signOut()
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit().clear().apply()
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }
}