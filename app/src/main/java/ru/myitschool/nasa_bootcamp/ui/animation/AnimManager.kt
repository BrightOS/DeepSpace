package ru.myitschool.nasa_bootcamp.ui.animation.core

import android.animation.Animator
import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.ViewRefresh

abstract class Anim(
    protected val animInstances: List<AnimInstance>,
    protected val viewToMove: View,
    protected val viewRefresh: ViewRefresh) {

    abstract fun getAnimators(): List<Animator>
    abstract fun change()

}
