package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View

class PositionAnimInsnanceRightOf(var otherView: View) : PositionAnimInsnance() {

    init {
        viewsDependencies.add(otherView)
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionRightOfView(otherView) + getMargin(viewToMove)
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}
