package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.myitschool.nasa_bootcamp.R
import java.text.SimpleDateFormat
import java.util.*

public fun loadImage(context: Context, url: String?, view : ImageView) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

fun convertDateFromUnix(date : Int) : String{
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
    val date = Date(date * 1000L)

    return sdf.format(date)
}
