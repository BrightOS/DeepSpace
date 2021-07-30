package ru.myitschool.nasa_bootcamp.data.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class PostItem(val type: Int, var text: String?, val bitmap: Bitmap?, val imagePath: Uri?)
