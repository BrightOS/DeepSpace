package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

public fun loadImage(
    context: Context,
    url: String?,
    view: ImageView,
    requestListener: RequestListener<Drawable>
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(requestListener)
        .into(view)
}

public fun loadImage(context: Context, url: String?, view: ImageView) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

suspend fun downloadFirebaseImage(storageRef: StorageReference): LiveData<Data<out Bitmap>> {
    val returnData = MutableLiveData<Data<out Bitmap>>()
    try {
        var tempLocalFile: File? = null
        kotlin.runCatching {
            tempLocalFile = File.createTempFile("Images", "bmp")
        }
        storageRef.getFile(tempLocalFile!!).addOnSuccessListener {
            returnData.postValue(Data.Ok(BitmapFactory.decodeFile(tempLocalFile!!.absolutePath)))
        }.addOnFailureListener {
            returnData.postValue(Data.Error(it.message.toString()))
        }.await()
    } catch (e: Exception) {
        returnData.postValue(Data.Error(e.message.toString()))
    }
    return returnData
}

fun convertDateFromUnix(date: Int): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
    val date = Date(date * 1000L)

    return sdf.format(date)
}

fun getDateFromUnixTimestamp(time: Long): String =
    SimpleDateFormat.getDateTimeInstance().format(Date(time * 1000))