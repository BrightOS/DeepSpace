package ru.berserkers.deepspace.ui.animation.attributes

import android.animation.Animator
import android.view.View

import ru.berserkers.deepspace.ui.animation.AnimInstance

abstract class AttributesAnimInstance : AnimInstance() {
    abstract fun getAnimator(viewToMove: View): Animator?
}
