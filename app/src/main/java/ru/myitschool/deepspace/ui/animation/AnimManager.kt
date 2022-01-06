package ru.myitschool.deepspace.ui.animation

import android.animation.Animator
import android.view.View

abstract class AnimManager(
    protected val animInstances: List<AnimInstance>,
    protected val viewToAnimate: View,
    protected val viewRefresh: ViewRefresh) {

    abstract fun getAnimators(): List<Animator>
    abstract fun change()
}
