package ru.berserkers.deepspace.ui.animation

import android.view.View


class ViewRefresh {
    private val viewToChange: MutableMap<View, ViewInstance> = mutableMapOf()

    fun setInstanceForView(view: View, viewInstance: ViewInstance) {
        viewToChange.put(view, viewInstance)
    }

    @JvmOverloads
    fun finalPositionLeftOfView(view: View, itsMe: Boolean = false): Float {
        var finalX: Float? = null

        val anim = viewToChange[view]
        if (anim != null) {
            val futurPositionX = anim.getFuturPositionX()
            if (futurPositionX != null) {
                finalX = futurPositionX
            }
        }

        if (finalX == null) {
            finalX = view.x
        }

        if (itsMe) {
            finalX -= (view.width - this.finalWidthOfView(view)) / 2f
        }

        return finalX
    }

    fun finalPositionRightOfView(view: View): Float {
        return finalPositionLeftOfView(view) + finalWidthOfView(view)
    }

    @JvmOverloads
    fun finalPositionTopOfView(view: View, itsMe: Boolean = false): Float {
        var finalTop: Float? = null

        val anim = viewToChange[view]
        if (anim != null) {
            val futurPositionY = anim.getFuturPositionY()
            if (futurPositionY != null) {
                finalTop = futurPositionY
            }
        }

        if (finalTop == null) {
            finalTop = 1f * view.top
        }

        if (itsMe) {
            finalTop -= (view.height - this.finalHeightOfView(view)) / 2f
        }

        return finalTop
    }

    fun finalPositionBottomOfView(view: View): Float {
        return finalPositionTopOfView(view) + finalHeightOfView(view)
    }

    fun finalCenterXOfView(view: View): Float {
        return if (viewToChange.containsKey(view)) {
            viewToChange[view]!!.getFuturPositionX()!! + finalWidthOfView(view) / 2f
        } else {
            view.left + view.width / 2f
        }
    }

    fun finalCenterYOfView(view: View): Float {
        return if (viewToChange.containsKey(view)) {
            viewToChange[view]!!.getFuturPositionY()!! + finalHeightOfView(view) / 2f
        } else {
            view.top + view.height / 2f
        }
    }

    fun finalWidthOfView(view: View): Float {
        var with = view.width.toFloat()
        if (viewToChange.containsKey(view)) {
            val anim = viewToChange[view]
            with = widthScaled(anim!!, with)
        }
        return with
    }

    private fun widthScaled(viewInstance: ViewInstance, width: Float): Float {
        var w = width
        val scaleX = viewInstance.getWillHasScaleX()
        if (scaleX != 1f) {
            w = scaleX * width
        }
        return w
    }

    fun finalHeightOfView(view: View): Float {
        var height = view.height.toFloat()
        if (viewToChange.containsKey(view)) {
            val anim = viewToChange[view]
            height = heightScaled(anim!!, height)
        }
        return height
    }

    private fun heightScaled(viewInstance: ViewInstance, height: Float): Float {
        var h = height
        val scaleY = viewInstance.getWillHasScaleY()
        if (scaleY != 1f) {
            h = scaleY * height
        }
        return h
    }
}
