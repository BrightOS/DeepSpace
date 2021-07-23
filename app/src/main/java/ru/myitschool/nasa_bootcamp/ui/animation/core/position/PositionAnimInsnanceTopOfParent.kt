package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View

class PositionAnimInsnanceTopOfParent : PositionAnimInsnance() {

    init {
        isForPositionY = true
    }

    override fun getChangedX(viewToMove: View): Float? {
        return null
    }

    override fun getChangedY(viewToMove: View): Float {
        return getMargin(viewToMove)
    }
}
