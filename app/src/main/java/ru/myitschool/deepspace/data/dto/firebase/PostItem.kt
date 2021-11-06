package ru.myitschool.deepspace.data.dto.firebase

import android.graphics.Bitmap
import android.net.Uri

data class PostItem(val type: Int, var text: String?, val bitmap: Bitmap?, val imagePath: Uri?)
