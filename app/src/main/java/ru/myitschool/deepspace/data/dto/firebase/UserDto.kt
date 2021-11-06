package ru.myitschool.deepspace.data.dto.firebase

class UserDto(val name: String, val avatarUrl: String?, val id: String) {
    constructor() : this("","", "")
}