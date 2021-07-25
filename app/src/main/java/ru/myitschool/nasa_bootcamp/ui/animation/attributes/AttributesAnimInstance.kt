package ru.myitschool.nasa_bootcamp.ui.animation.attributes

import android.animation.Animator
import android.view.View

import ru.myitschool.nasa_bootcamp.ui.animation.AnimInstance

abstract class AttributesAnimInstance : AnimInstance() {
    abstract fun getAnimator(viewToMove: View): Animator?
}
