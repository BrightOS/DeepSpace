package ru.berserkers.deepspace.ui.animation.position

import android.view.View

class PositionAnimInstanceAlignBottom(val otherView: View) : PositionAnimInstance() {

    init {
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        return null
    }

    override fun getChangedY(viewToMove: View): Float{
        return viewRefresh!!.finalPositionBottomOfView(otherView) - getMargin(viewToMove) - viewToMove.height.toFloat()
    }
}
