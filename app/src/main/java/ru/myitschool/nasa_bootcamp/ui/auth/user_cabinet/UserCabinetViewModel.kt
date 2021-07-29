package ru.myitschool.nasa_bootcamp.ui.auth.user_cabinet

interface UserCabinetViewModel {
    suspend fun changeEmail(newEmail: String)
    suspend fun changeNickName(newNick: String)
    suspend fun changePassword(newPass: String)
}