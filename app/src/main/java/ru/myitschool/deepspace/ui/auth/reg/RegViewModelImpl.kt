package ru.myitschool.deepspace.ui.auth.reg

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.repository.FirebaseRepository
import ru.myitschool.deepspace.utils.Data
import javax.inject.Inject

/*
 * @author Danil Khairulin
 */
@HiltViewModel
class RegViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), RegViewModel {
    override suspend fun createUser(
        context: Context,
        userName: String,
        email: String,
        password: String,
        imagePath: Uri?
    ): Data<FirebaseUser> {
        return repository.createUser(context, userName, email, password, imagePath)
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}
