package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.core.AnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.core.pixelConverters

abstract class PositionAnimInsnance : AnimInstance() {

    var isForPositionY: Boolean = false
    var isForPositionX: Boolean = false
    var isForTranslationX: Boolean = false
    var isForTranslationY: Boolean = false

    var margin: Float? = null
    var marginDp: Float? = null

    abstract fun getChangedX(viewToMove: View): Float?
    abstract fun getChangedY(viewToMove: View): Float?

    fun getMargin(view: View): Float {
        return when {
            this.margin != null -> this.margin!!
            this.marginDp != null -> pixelConverters.dpToPx(view.context, marginDp!!)
            else -> 0f
        }
    }

}
