package ru.myitschool.deepspace.ui.animation.position

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

import ru.myitschool.deepspace.ui.animation.AnimInstance
import ru.myitschool.deepspace.ui.animation.AnimManager
import ru.myitschool.deepspace.ui.animation.ViewRefresh

class AnimPosition(animInstances: List<AnimInstance>, viewToMove: View, viewRefresh: ViewRefresh)
    : AnimManager(animInstances, viewToMove, viewRefresh) {

    private var positionX: Float? = null
    private var positionY: Float? = null

    private var translationX: Float? = null
    private var translationY: Float? = null

    fun getPositionX(): Float? {
        return if (translationX != null) {
            viewToAnimate.x + translationX!!
        } else {
            positionX
        }
    }

    fun getPositionY(): Float? {
        return if (translationX != null) {
            viewToAnimate.y + translationY!!
        } else {
            positionY
        }
    }

    override fun change() {
        animInstances.forEach { anim ->
            if (anim is PositionAnimInstance) {

                anim.viewRefresh = viewRefresh

                anim.getChangedX(viewToAnimate)?.let { calculatedValueX ->
                    if (anim.isForPositionX) {
                        positionX = calculatedValueX
                    }
                    if (anim.isForTranslationX) {
                        translationX = calculatedValueX
                    }
                }

                anim.getChangedY(viewToAnimate)?.let { calculatedValueY ->
                    if (anim.isForPositionY) {
                        positionY = calculatedValueY
                    }
                    if (anim.isForTranslationY) {
                        translationY = calculatedValueY
                    }
                }
            }
        }
    }

    override fun getAnimators(): List<Animator> {
        val animations = mutableListOf<Animator>()

        if (positionX != null) {
            animations.add(ObjectAnimator.ofFloat(viewToAnimate, View.X, viewRefresh.finalPositionLeftOfView(viewToAnimate, true)))
        }

        if (positionY != null) {
            animations.add(ObjectAnimator.ofFloat(viewToAnimate, View.Y, viewRefresh.finalPositionTopOfView(viewToAnimate, true)))
        }

        translationX?.let {
            animations.add(ObjectAnimator.ofFloat(viewToAnimate, View.TRANSLATION_X, it))
        }

        translationY?.let {
            animations.add(ObjectAnimator.ofFloat(viewToAnimate, View.TRANSLATION_Y, it))
        }

        return animations
    }
}
