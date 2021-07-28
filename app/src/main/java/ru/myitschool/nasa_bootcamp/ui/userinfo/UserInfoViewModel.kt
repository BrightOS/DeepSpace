package ru.myitschool.nasa_bootcamp.ui.userinfo

interface UserInfoViewModel{
    fun changePassword(oldPassword: String, newPassword: String)
    fun changeUserName(userName: String);

}