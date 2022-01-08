package ru.berserkers.deepspace.ui.animation.attributes

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView

class TextColorAnimInstance(private val textColor: Int) : AttributesAnimInstance() {

    override fun getAnimator(viewToMove: View): Animator? {
        if (viewToMove is TextView) {
            return ValueAnimator.ofInt(viewToMove.currentTextColor, textColor).apply {
                addUpdateListener { valueAnimator ->
                    viewToMove.setTextColor(valueAnimator.getAnimatedValue() as Int)
                }
                setEvaluator(ArgbEvaluator())
            }
        } else {
            return null
        }
    }
}
