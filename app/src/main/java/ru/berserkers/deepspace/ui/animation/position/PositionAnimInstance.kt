package ru.berserkers.deepspace.ui.animation.position

import android.view.View
import ru.berserkers.deepspace.ui.animation.AnimInstance
import ru.berserkers.deepspace.ui.animation.PixelConverters

abstract class PositionAnimInstance : AnimInstance() {

    var isForPositionY: Boolean = false
        protected set
    var isForPositionX: Boolean = false
        protected set
    var isForTranslationX: Boolean = false
        protected set
    var isForTranslationY: Boolean = false
        protected set

    var margin: Float? = null
    var marginDp: Float? = null

    abstract fun getChangedX(viewToMove: View): Float?
    abstract fun getChangedY(viewToMove: View): Float?

    fun getMargin(view: View): Float {
        return when {
            this.margin != null -> this.margin!!
            this.marginDp != null -> PixelConverters.dpToPx(view.context, marginDp!!)
            else -> 0f
        }
    }

}
