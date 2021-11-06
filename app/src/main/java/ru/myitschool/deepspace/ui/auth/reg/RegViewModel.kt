package ru.myitschool.deepspace.ui.auth.reg

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.utils.Data

/*
 * @author Danil Khairulin
 */
interface RegViewModel {
    suspend fun createUser(context: Context, userName: String, email: String, password: String, imagePath: Uri?): Data<out FirebaseUser>
    fun getViewModelScope(): CoroutineScope
}
