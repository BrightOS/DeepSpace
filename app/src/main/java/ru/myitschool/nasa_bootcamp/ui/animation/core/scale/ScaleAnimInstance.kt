package ru.myitschool.nasa_bootcamp.ui.animation.core.scale

import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.core.AnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.core.pixelConverters


abstract class ScaleAnimInstance(gravityHorizontal: Int?, gravityVertical: Int?) : AnimInstance() {

    var toDp = false
    var keepRatio = false
    var gravityHorizontal: Int? = null
        private set
    var gravityVertical: Int? = null
        private set

    init {
        if (gravityHorizontal != null) {
            this.gravityHorizontal = gravityHorizontal
        }
        if (gravityVertical != null) {
            this.gravityVertical = gravityVertical
        }
    }

    protected fun dpToPixels(value: Float, view: View): Int {
        val v = pixelConverters.dpToPx(view.context, value).toInt()
        toDp = false
        return v
    }

    abstract fun getChangedValueScaleX(viewToMove: View): Float?
    abstract fun getChangedValueScaleY(viewToMove: View): Float?
}
