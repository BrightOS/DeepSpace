package ru.myitschool.nasa_bootcamp.ui.auth.reg

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import javax.inject.Inject

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
    ): LiveData<Data<out FirebaseUser>> {
        return repository.createUser(context, userName, email, password, imagePath)
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}