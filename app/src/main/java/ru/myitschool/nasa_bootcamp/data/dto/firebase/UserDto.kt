package ru.myitschool.nasa_bootcamp.data.dto.firebase

import android.net.Uri

class UserDto(val name: String, val avatarUrl: String?, val id: String) {
    constructor() : this("","", "")
}