package ru.myitschool.nasa_bootcamp.data.model

import android.graphics.Bitmap

data class Post(val title: String, var text: String, val data: Bitmap?, val type: Int) {
    constructor() : this("", "", null, -1)
}