package ru.myitschool.nasa_bootcamp.utils

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
}