package ru.myitschool.deepspace.ui.animation.alpha

import android.view.View

import ru.myitschool.deepspace.ui.animation.AnimInstance

abstract class AlphaInstance : AnimInstance() {
    abstract fun getChangedAlpha(viewToAnimate: View): Float?
}
