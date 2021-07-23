package ru.myitschool.nasa_bootcamp.ui.animation.core

import android.content.Context
import android.util.TypedValue

object pixelConverters {

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }


}