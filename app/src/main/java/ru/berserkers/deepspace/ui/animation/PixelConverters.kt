package ru.berserkers.deepspace.ui.animation

import android.content.Context
import android.util.TypedValue

object PixelConverters {

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

    fun pxToDp(context: Context, pixel: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixel, context.resources.displayMetrics)
    }

    fun spToDp(context: Context, pixel: Float): Float {
        return pixel / context.resources.displayMetrics.scaledDensity
    }
}
