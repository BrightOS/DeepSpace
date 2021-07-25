package ru.myitschool.nasa_bootcamp.ui.animation.position

import android.view.View

class PositionAnimInstanceCenterInParent(var horizontal: Boolean, var vertical: Boolean) : PositionAnimInstance() {

    init {
        isForPositionX = true
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        val viewParent = viewToMove.parent
        if (viewParent is View && horizontal) {
            val parentView = viewParent as View
            return parentView.width / 2f - viewToMove.width / 2f
        }
        return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        val viewParent = viewToMove.parent
        if (viewParent is View && vertical) {
            val parentView = viewParent as View
            return parentView.height / 2f - viewToMove.height / 2f
        }
        return null
    }
}
