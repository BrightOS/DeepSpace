package ru.myitschool.deepspace.ui.animation.scale

import android.view.View


class ScaleInstanceWidth(private var width: Int, gravityHorizontal: Int?, gravityVertical: Int?) :
    ScaleInstance(gravityHorizontal, gravityVertical) {

    override fun getChangedValueScaleX(viewToMove: View): Float {
        if (toDp) {
            this.width = dpToPx(this.width.toFloat(), viewToMove)
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
