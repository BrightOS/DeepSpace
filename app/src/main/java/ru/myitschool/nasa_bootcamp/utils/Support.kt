package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.util.Preconditions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun loadImage(
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

fun loadImageCircle(context: Context, url: String?, view: ImageView) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()
        .into(view)
}


fun loadImage(context: Context, url: String?, view: ImageView) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

suspend fun downloadFirebaseImage(storageRef: StorageReference): Data<Bitmap> {
    try {
        var tempLocalFile: File? = null
        runCatching {
            tempLocalFile = File.createTempFile("Images", "bmp")
        }

        var image: Data<Bitmap> = Data.Loading
        storageRef.getFile(tempLocalFile!!).addOnSuccessListener {
            image = Data.Ok(BitmapFactory.decodeFile(tempLocalFile!!.absolutePath))
        }.addOnFailureListener {
            image = Data.Error(it.message.toString())
        }.await()
        return image
    } catch (e: Exception) {
        return Data.Error(e.message.toString())
    }
}

fun convertDateFromUnix(date: Long): String {
    val sdf = SimpleDateFormat("MMMM d. yyyy. hh:mm", Locale.US)
    val date = Date(date * 1000L)

    val finalString = sdf.format(date)

    val calendar = GregorianCalendar()
    calendar.time = date

    return finalString
}

fun getDateFromUnixTimestamp(time: Long): String =
    SimpleDateFormat.getDateTimeInstance().format(Date(time))

fun parseNewsDate(input: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    val date = dateFormat.parse(input)
    return if (date != null)
        getDateFromUnixTimestamp(date.time)
    else "Unknown date"
}

fun TextInputEditText.checkForErrors(inputLayout: TextInputLayout) {
    doOnTextChanged { text, start, before, count ->
        inputLayout.isErrorEnabled = false
    }
}

@ColorInt
fun getColorFromAttributes(context: Context, resID: Int): Int {
    val typedValue = TypedValue()
    val theme: Resources.Theme = context.theme!!
    theme.resolveAttribute(resID, typedValue, true)
    return typedValue.data
}

fun dateToAmericanFormat(dateString: String, addTime: Boolean = false): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val date = inputFormat.parse(dateString)

    val outputFormat = SimpleDateFormat(
        if (!addTime)
            "MMMM d. yyyy"
        else
            "MMMM d. yyyy. hh:mm",
        Locale.US
    )
    val finalString: String = outputFormat.format(date!!)

    val calendar = GregorianCalendar()
    calendar.time = date

    return finalString.addSubstringAtIndex(
        getDayOfMonthSuffix(
            calendar.get(Calendar.DAY_OF_MONTH)
        ),
        finalString.indexOf('.')
    )
}

fun getDayOfMonthSuffix(n: Int): String {
    Preconditions.checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
    return if (n >= 11 && n <= 13) {
        "th"
    } else when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

fun String.addSubstringAtIndex(string: String, index: Int) =
    StringBuilder(this).apply { insert(index, string) }.toString()