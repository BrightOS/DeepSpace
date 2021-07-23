package ru.myitschool.nasa_bootcamp.ui.animation.core.scale

import android.view.View


class ScaleAnimInstanceWidth(
    private var width: Int,
    gravityHorizontal: Int?,
    gravityVertical: Int?
) : ScaleAnimInstance(gravityHorizontal, gravityVertical) {

    override fun getChangedValueScaleX(viewToMove: View): Float {
        if (toDp) {
            this.width = dpToPixels(this.width.toFloat(), viewToMove)
        }

        val viewToMoveWidth = viewToMove.width
        return if (this.width == 0 || viewToMoveWidth.toFloat() == 0f) {
            0f
        } else 1f * this.width / viewToMoveWidth
    }

    override fun getChangedValueScaleY(viewToMove: View): Float? {
        return if (keepRatio) {
            getChangedValueScaleX(viewToMove)
        } else null
    }

}
