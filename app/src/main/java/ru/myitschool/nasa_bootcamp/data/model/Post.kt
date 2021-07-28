package ru.myitschool.nasa_bootcamp.data.model

import android.graphics.Bitmap

data class Post(val title: String, val data: List<>) {
    constructor() : this("", "", null)
}