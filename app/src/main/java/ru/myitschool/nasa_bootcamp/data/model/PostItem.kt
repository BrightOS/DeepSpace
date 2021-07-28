package ru.myitschool.nasa_bootcamp.data.model

import android.graphics.Bitmap

sealed class PostItem(val type: Int, var text: String?, val bitmap: Bitmap?)
