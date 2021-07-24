package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.view.View

//Выравнивать относительно левой стороны
class PositionAnimInsnanceAlignLeft(var otherView: View) : PositionAnimInsnance() {

    init {
        viewsDependencies.add(otherView)
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionLeftOfView(otherView) + getMargin(viewToMove)
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}

//Выравнивать относительно правой стороны
class PositionAnimInsnanceAlignRight(var otherView: View) : PositionAnimInsnance(){

    init {
        viewsDependencies.add(otherView)
        isForPositionX = true
    }

    override fun getChangedX(viewToMove: View): Float {
        return viewRefresh!!.finalPositionRightOfView(otherView) - getMargin(viewToMove) - viewToMove.width.toFloat()
    }

    override fun getChangedY(viewToMove: View): Float? {
        return null
    }
}


//Разместить в центре между двумя view
class PositionAnimInsnanceCenterBetweenViews(private val view1: View, private val view2: View, private val horizontal: Boolean, private val vertical: Boolean) : PositionAnimInsnance() {

    init {
        isForPositionY = true
        isForPositionX = true

        viewsDependencies.add(view1)
        viewsDependencies.add(view2)
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


