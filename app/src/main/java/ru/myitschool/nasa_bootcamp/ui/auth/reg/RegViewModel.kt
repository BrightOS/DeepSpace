package ru.myitschool.nasa_bootcamp.ui.auth.reg

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import ru.myitschool.nasa_bootcamp.utils.Data

interface RegViewModel {
    fun register(userName: String, email: String, password: String): LiveData<Data<out FirebaseUser>>
}
