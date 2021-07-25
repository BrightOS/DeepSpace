package ru.myitschool.nasa_bootcamp.ui.animation.position

import android.view.View

class PositionAnimInstanceLeftOf(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionLeftOfView(otherView) - getMargin(viewToMove) - viewToMove.width.toFloat()
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}

class PositionAnimInstanceRightOf(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionRightOfView(otherView) + getMargin(viewToMove)
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}

