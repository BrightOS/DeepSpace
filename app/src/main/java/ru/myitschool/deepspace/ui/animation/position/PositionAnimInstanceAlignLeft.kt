package ru.myitschool.deepspace.ui.animation.position

import android.view.View

class PositionAnimInstanceAlignLeft(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionLeftOfView(otherView) + getMargin(viewToMove)
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}
class PositionAnimInstanceAlignRight(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionRightOfView(otherView) - getMargin(viewToMove) - viewToMove.width.toFloat()
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}

class PositionAnimInstanceAlignTop(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        return null
    }

    override fun getChangedY(viewToMove: View): Float {
        return viewRefresh!!.finalPositionTopOfView(otherView) + getMargin(viewToMove)
    }
}

class PositionAnimInstanceBottomOfParent : PositionAnimInstance() {

    init {
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        val viewParent = viewToMove.parent
        if (viewParent is View) {
            val parentView = viewParent as View
            return parentView.height.toFloat() - getMargin(viewToMove) - viewRefresh!!.finalHeightOfView(viewToMove)
        }
        return null
    }
}

