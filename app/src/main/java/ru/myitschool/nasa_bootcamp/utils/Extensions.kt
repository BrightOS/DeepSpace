package ru.myitschool.nasa_bootcamp.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Extensions {
    fun TextInputEditText.checkForErrors(inputLayout: TextInputLayout){
        doOnTextChanged{ text, start, before, count ->
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
}