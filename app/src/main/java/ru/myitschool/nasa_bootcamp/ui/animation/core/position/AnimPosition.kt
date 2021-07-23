package ru.myitschool.nasa_bootcamp.ui.animation.core.position

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.ViewRefresh

import ru.myitschool.nasa_bootcamp.ui.animation.core.AnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.core.Anim


class AnimPosition(
    animInstances: List<AnimInstance>,
    viewToMove: View,
    viewRefresh: ViewRefresh
) : Anim(animInstances, viewToMove, viewRefresh) {

    private var positionX: Float? = null
    private var positionY: Float? = null

    private var translationX: Float? = null
    private var translationY: Float? = null

    fun getPositionX(): Float? {
        return if (translationX != null) viewToMove.x + translationX!! else positionX
    }

    fun getPositionY(): Float? {
        return if (translationX != null) viewToMove.y + translationY!! else positionY

    }

    override fun change() {
        animInstances.forEach { animInstance ->
            if (animInstance is PositionAnimInsnance) {

                animInstance.viewRefresh = viewRefresh

                animInstance.getChangedX(viewToMove)?.let { calculatedValueX ->
                    if (animInstance.isForPositionX) positionX = calculatedValueX
                    if (animInstance.isForTranslationX) translationX = calculatedValueX
                }

                animInstance.getChangedY(viewToMove)?.let { calculatedValueY ->
                    if (animInstance.isForPositionY) positionY = calculatedValueY
                    if (animInstance.isForTranslationY) translationY = calculatedValueY

                }
            }
        }
    }

    override fun getAnimators(): List<Animator> {
        val animations = mutableListOf<Animator>()

        if (positionX != null) {
            animations.add(
                ObjectAnimator.ofFloat(
                    viewToMove,
                    View.X,
                    viewRefresh.finalPositionLeftOfView(viewToMove, true)
                )
            )
        }

        if (positionY != null) {
            animations.add(
                ObjectAnimator.ofFloat(
                    viewToMove,
                    View.Y,
                    viewRefresh.finalPositionTopOfView(viewToMove, true)
                )
            )
        }

        translationX?.let {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.TRANSLATION_X, it))
        }

        translationY?.let {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.TRANSLATION_Y, it))
        }

        return animations
    }
}
