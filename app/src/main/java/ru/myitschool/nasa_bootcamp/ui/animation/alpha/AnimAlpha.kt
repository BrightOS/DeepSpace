package ru.myitschool.nasa_bootcamp.ui.animation.alpha

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.AnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.AnimManager
import ru.myitschool.nasa_bootcamp.ui.animation.ViewRefresh

class AnimAlpha(
    animInstances: List<AnimInstance>,
    viewToMove: View, viewRefresh: ViewRefresh
) : AnimManager(animInstances, viewToMove, viewRefresh) {

    private var alpha: Float? = null

    override fun change() {
        animInstances.forEach { animation ->
            if (animation is AlphaInstance) {
                animation.getChangedAlpha(viewToAnimate)?.let {
                    this.alpha = it
                }
            }
        }
    }

    override fun getAnimators(): List<Animator> {
        val animations = mutableListOf<Animator>()

        change()

        alpha?.let { alpha ->

            val objectAnimator = ObjectAnimator.ofFloat(viewToAnimate, View.ALPHA, alpha)

            if (alpha == 0f) {
                if (viewToAnimate.alpha != 0f) {
                    animations.add(objectAnimator)

                    objectAnimator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            viewToAnimate.visibility = View.INVISIBLE
                        }
                    })
                }
            } else if (alpha == 1f) {
                if (viewToAnimate.alpha != 1f) {
                    animations.add(objectAnimator)

                    objectAnimator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            viewToAnimate.visibility = View.VISIBLE
                        }
                    })
                }
            } else {
                animations.add(objectAnimator)

                objectAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        viewToAnimate.visibility = View.VISIBLE
                    }
                })
            }

        }

        return animations
    }
}
