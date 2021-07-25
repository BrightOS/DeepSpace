package ru.myitschool.nasa_bootcamp.ui.animation.scale

import android.view.View


class ScaleInstanceValues(private val scaleX: Float, private val scaleY: Float, gravityHorizontal: Int?, gravityVertical: Int?) : ScaleInstance(gravityHorizontal, gravityVertical) {

    override fun getChangedValueScaleX(viewToMove: View): Float {
        return scaleX
    }

    override fun getChangedValueScaleY(viewToMove: View): Float {
        return scaleY
    }
}
