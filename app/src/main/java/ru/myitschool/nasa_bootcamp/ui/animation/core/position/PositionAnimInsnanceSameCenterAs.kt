package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View

class PositionAnimInsnanceSameCenterAs(var otherView: View, private val horizontal: Boolean, private val vertical: Boolean)  : PositionAnimInsnance(){

    init {
        viewsDependencies.add(otherView)
        isForPositionX = true
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        if (horizontal) {
            val x = viewRefresh!!.finalPositionLeftOfView(otherView)
            val myWidth = viewToMove.width / 2f
            val hisWidth = viewRefresh!!.finalWidthOfView(otherView) / 2f

            return if (myWidth > hisWidth) x - myWidth + hisWidth else x - hisWidth + myWidth

        } else
            return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        if (vertical) {
            val y = viewRefresh!!.finalPositionTopOfView(otherView)
            val myHeight = viewToMove.height / 2f
            val hisHeight = viewRefresh!!.finalHeightOfView(otherView) / 2f

            return if (myHeight > hisHeight) y + myHeight - hisHeight else y + hisHeight - myHeight

        } else
            return null
    }

}
