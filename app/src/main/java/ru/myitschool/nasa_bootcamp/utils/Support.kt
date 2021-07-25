package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

public fun loadImage(context: Context, url: String?, view : ImageView) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

fun quickSort(array: IntArray, low: Int, high: Int) {
    var i = low
    var j = high
    val pivot = array[(i + j) / 2]
    var temp: Int
    while (i <= j) {
        while (array[i] < pivot) i++
        while (array[j] > pivot) j--
        if (i <= j) {
            temp = array[i]
            array[i] = array[j]
            array[j] = temp
            i++
            j--
        }
    }
    if (j > low) quickSort(array, low, j)
    if (i < high) quickSort(array, i, high)
}
