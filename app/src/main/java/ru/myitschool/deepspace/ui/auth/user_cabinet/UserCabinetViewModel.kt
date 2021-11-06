package ru.myitschool.deepspace.ui.auth.user_cabinet

/*
 * @author Danil Khairulin
 */
interface UserCabinetViewModel {
    suspend fun changeEmail(newEmail: String)
    suspend fun changeNickName(newNick: String)
    suspend fun changePassword(newPass: String)
}