package ru.myitschool.nasa_bootcamp.ui.registration

interface RegistrationViewModel {
    suspend fun CreateUser(email: String, password: String)
    suspend fun AuthenticateUser(email: String, password: String)
    suspend fun SignOutUser()
}