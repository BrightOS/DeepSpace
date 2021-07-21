package ru.myitschool.nasa_bootcamp.ui.registration

interface RegistrationViewModel {
    suspend fun createUser(email: String, password: String)
    suspend fun authenticateUser(email: String, password: String)
    suspend fun signOutUser()
}