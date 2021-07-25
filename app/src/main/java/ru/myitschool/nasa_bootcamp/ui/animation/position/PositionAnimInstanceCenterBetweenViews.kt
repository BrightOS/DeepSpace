package ru.myitschool.nasa_bootcamp.ui.animation.position

import android.view.View

class PositionAnimInstanceCenterBetweenViews(private val view1: View, private val view2: View, private val horizontal: Boolean, private val vertical: Boolean) : PositionAnimInstance() {

    init {
        isForPositionY = true
        isForPositionX = true

    }

    override fun getChangedX(viewToMove: View): Float? {
        if (horizontal) {
            val centerXView1 = (view1.left + view1.width / 2f).toInt()
            val centerXView2 = (view2.left + view2.width / 2f).toInt()

            return (centerXView1 + centerXView2) / 2f - viewToMove.width / 2f
        }
        return null
    }

    override fun getChangedY(viewToMove: View): Float? {
        if (vertical) {
            val centerYView1 = (view1.top + view1.height / 2f).toInt()
            val centerYView2 = (view2.top + view2.height / 2f).toInt()

            return (centerYView1 + centerYView2) / 2f - viewToMove.height / 2f
        }
        return null
    }
}
