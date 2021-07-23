package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View

class PositionAnimInsnanceLeftOfParent : PositionAnimInsnance() {
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
class PositionAnimInsnanceRightOfParent : PositionAnimInsnance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        val viewParent = viewToMove.parent
        if (viewParent is View) {
            val parentView = viewParent as View
            return parentView.width.toFloat() - getMargin(viewToMove) - viewRefresh!!.finalWidthOfView(viewToMove)
        }
        return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}
