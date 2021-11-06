package ru.myitschool.deepspace.ui.animation.position

import android.view.View

class PositionAnimInstanceSameCenterAs(private val otherView: View, private val horizontal: Boolean, private val vertical: Boolean)
    : PositionAnimInstance() {

    init {
        isForPositionX = true
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        if (horizontal) {
            val x = viewRefresh!!.finalPositionLeftOfView(otherView)
            val myWidth = viewToMove.width / 2f
            val hisWidth = viewRefresh!!.finalWidthOfView(otherView) / 2f

            return if (myWidth > hisWidth) {
                x - myWidth + hisWidth
            } else {
                x - hisWidth + myWidth
            }
        } else
            return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        if (vertical) {
            val y = viewRefresh!!.finalPositionTopOfView(otherView)
            val myHeight = viewToMove.height / 2f
            val hisHeight = viewRefresh!!.finalHeightOfView(otherView) / 2f

            return if (myHeight > hisHeight) {
                y + myHeight - hisHeight
            } else {
                y + hisHeight - myHeight
            }
        } else
            return null
    }

}
