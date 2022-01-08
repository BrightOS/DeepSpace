package ru.berserkers.deepspace.ui.animation.alpha

import android.view.View

import ru.berserkers.deepspace.ui.animation.AnimInstance

abstract class AlphaInstance : AnimInstance() {
    abstract fun getChangedAlpha(viewToAnimate: View): Float?
}
