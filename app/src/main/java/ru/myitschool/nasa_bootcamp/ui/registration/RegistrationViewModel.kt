package ru.myitschool.nasa_bootcamp.ui.registration

import android.graphics.Bitmap
import android.net.Uri

interface RegistrationViewModel {
    suspend fun createUser(email: String, password: String, username: String, imagePath: Uri?)
    suspend fun authenticateUser(email: String, password: String)
    suspend fun signOutUser()
}