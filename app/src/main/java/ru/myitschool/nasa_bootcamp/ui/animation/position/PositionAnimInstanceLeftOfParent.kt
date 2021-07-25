package ru.myitschool.nasa_bootcamp.ui.animation.position

import android.view.View

class PositionAnimInstanceLeftOfParent : PositionAnimInstance() {
    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return getMargin(viewToMove)
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}

class PositionAnimInstanceRightOfParent : PositionAnimInstance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        val viewParent = viewToMove.parent
        if (viewParent is View) {
            val parentView = viewParent as View
            return parentView.width.toFloat() - getMargin(viewToMove) - viewRefresh!!.finalWidthOfView(
                viewToMove
            )
        }
        return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}
