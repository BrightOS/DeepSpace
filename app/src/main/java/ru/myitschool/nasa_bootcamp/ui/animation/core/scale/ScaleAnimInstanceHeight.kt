package ru.myitschool.nasa_bootcamp.ui.animation.core.scale

import android.view.View

class ScaleAnimInstanceHeight(
    private var height: Int,
    gravityHorizontal: Int?,
    gravityVertical: Int?
) : ScaleAnimInstance(gravityHorizontal, gravityVertical) {

    override fun getChangedValueScaleX(viewToMove: View): Float? {
        return if (keepRatio) {
            getChangedValueScaleY(viewToMove)
        } else null
    }

    override fun getChangedValueScaleY(viewToMove: View): Float {

        if (toDp) {
            this.height = dpToPixels(this.height.toFloat(), viewToMove)
        }

        val viewToMoveHeight = viewToMove.height
        return if (this.height == 0 || viewToMoveHeight.toFloat() == 0f) {
            0f
        } else 1f * this.height / viewToMoveHeight
    }
}
