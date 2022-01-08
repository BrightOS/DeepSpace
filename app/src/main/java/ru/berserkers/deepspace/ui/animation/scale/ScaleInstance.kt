package ru.berserkers.deepspace.ui.animation.scale

import android.view.View

import ru.berserkers.deepspace.ui.animation.AnimInstance
import ru.berserkers.deepspace.ui.animation.PixelConverters

abstract class ScaleInstance(gravityHorizontal: Int?, gravityVertical: Int?) : AnimInstance() {

    var toDp = false
    var keepRatio = false
    var gravityHorizontal: Int? = null
        private set
    var gravityVertical: Int? = null
        private set

    init {
        if (gravityHorizontal != null) this.gravityHorizontal = gravityHorizontal
        if (gravityVertical != null) this.gravityVertical = gravityVertical
    }

    protected fun dpToPx(value: Float, view: View): Int {
        val v = PixelConverters.dpToPx(view.context, value).toInt()
        toDp = false
        return v
    }

    abstract fun getChangedValueScaleX(viewToMove: View): Float?
    abstract fun getChangedValueScaleY(viewToMove: View): Float?
}
