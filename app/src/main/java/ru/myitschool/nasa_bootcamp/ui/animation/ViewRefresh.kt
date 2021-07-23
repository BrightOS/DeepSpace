package ru.myitschool.nasa_bootcamp.ui.animation

import android.view.View


class ViewRefresh {
    private val viewsForAnimating: MutableMap<View, ViewInstance> = mutableMapOf()

    fun setAnimatingForView(view: View, viewInstance: ViewInstance) {
        viewsForAnimating.put(view, viewInstance)
    }


    fun finalPositionLeftOfView(view: View, itsMe: Boolean = false): Float {
        var finalX: Float? = null

        val viewExpectation = viewsForAnimating[view]
        if (viewExpectation != null) {
            val futurPositionX = viewExpectation.getFuturePositionX()
            if (futurPositionX != null) {
                finalX = futurPositionX
            }
        }
        if (finalX == null) finalX = view.x
        if (itsMe) finalX -= (view.width - this.finalWidthOfView(view)) / 2f

        return finalX
    }

    fun finalPositionRightOfView(view: View): Float {
        return finalPositionLeftOfView(view) + finalWidthOfView(view)
    }


    fun finalPositionTopOfView(view: View, itsMe: Boolean = false): Float {
        var finalTop: Float? = null

        val viewExpectation = viewsForAnimating[view]
        if (viewExpectation != null) {
            val futurPositionY = viewExpectation.getFuturePositionY()
            if (futurPositionY != null) {
                finalTop = futurPositionY
            }
        }
        if (finalTop == null) finalTop = 1f * view.top
        if (itsMe) finalTop -= (view.height - this.finalHeightOfView(view)) / 2f

        return finalTop
    }

    fun finalWidthOfView(view: View): Float {
        var width = view.width.toFloat()
        if (viewsForAnimating.containsKey(view)) {
            val viewToAnimate = viewsForAnimating[view]
            width = widthScaled(viewToAnimate!!, width)

        }
        return width
    }

    private fun widthScaled(viewInstance: ViewInstance, width: Float): Float {
        var w = width
        val scaleX = viewInstance.getWillHasScaleX()
        if (scaleX != 1f) w = scaleX * width
        return w
    }

    fun finalHeightOfView(view: View): Float {
        var height = view.height.toFloat()
        if (viewsForAnimating.containsKey(view)) {
            val viewExpectation = viewsForAnimating[view]

            height = heightScaled(viewExpectation!!, view, height)
        }
        return height
    }

    private fun heightScaled(viewInstance: ViewInstance, view: View, height: Float): Float {
        var h = height
        val scaleY = viewInstance.getWillHasScaleY()
        if (scaleY != 1f) {
            h = scaleY * height
        }
        return h
    }

}
