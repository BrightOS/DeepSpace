package ru.myitschool.deepspace.data.fb_general

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import ru.myitschool.deepspace.utils.Data
import ru.myitschool.deepspace.utils.downloadFirebaseImage

/*
 * @author Yana Glad
 */
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

    fun signOutUser(context: Context): LiveData<Data<out FirebaseUser>> {
        val returnData: MutableLiveData<Data<out FirebaseUser>> = MutableLiveData()
        try {
            authenticator.signOut()
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit().clear().apply()
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }
}