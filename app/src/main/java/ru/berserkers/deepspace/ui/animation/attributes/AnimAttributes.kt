package ru.berserkers.deepspace.ui.animation.attributes

import android.animation.Animator
import android.view.View
import ru.berserkers.deepspace.ui.animation.AnimInstance
import ru.berserkers.deepspace.ui.animation.AnimManager
import ru.berserkers.deepspace.ui.animation.ViewRefresh

class AnimAttributes(animInstances: List<AnimInstance>, viewToMove: View, viewRefresh: ViewRefresh)
    : AnimManager(animInstances, viewToMove, viewRefresh) {

    val animations: MutableList<Animator> = mutableListOf()

    override fun change() {
        animInstances.forEach { animInstance ->
            if (animInstance is AttributesAnimInstance) {
                animInstance.viewRefresh = viewRefresh
                animInstance.getAnimator(viewToAnimate)?.let { animator ->
                    animations.add(animator)
                }
            }
        }
    }

    override fun getAnimators(): List<Animator> {
        return animations
    }
}
