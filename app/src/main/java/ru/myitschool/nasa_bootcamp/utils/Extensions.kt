package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.util.Preconditions.checkArgument
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.internal.http.toHttpDateString
import java.text.SimpleDateFormat
import java.util.*

object Extensions {
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

    fun dateToAmericanFormat(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("MMMM d. yyyy", Locale.US)
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
        checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
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
}