package ru.myitschool.deepspace.ui.animation.alpha

import android.view.View

class AlphaInstanceValue(private val alpha: Float) : AlphaInstance() {
    override fun getChangedAlpha(viewToAnimate: View): Float {
        return alpha
    }
}
