package ru.myitschool.nasa_bootcamp.ui.animation.core.scale

import android.view.View


class ScaleAnimInstanceValues(private val scaleX: Float, private val scaleY: Float, gravityHorizontal: Int?, gravityVertical: Int?) : ScaleAnimInstance(gravityHorizontal, gravityVertical) {

    override fun getChangedValueScaleX(viewToMove: View): Float {
        return scaleX
    }

    override fun getChangedValueScaleY(viewToMove: View): Float {
        return scaleY
    }
}
