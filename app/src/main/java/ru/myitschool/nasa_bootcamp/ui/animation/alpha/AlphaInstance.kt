package ru.myitschool.nasa_bootcamp.ui.animation.alpha

import android.view.View

import ru.myitschool.nasa_bootcamp.ui.animation.AnimInstance

abstract class AlphaInstance : AnimInstance() {
    abstract fun getChangedAlpha(viewToAnimate: View): Float?
}
