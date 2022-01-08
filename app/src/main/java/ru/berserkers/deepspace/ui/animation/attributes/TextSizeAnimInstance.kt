package ru.berserkers.deepspace.ui.animation.attributes

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import ru.berserkers.deepspace.ui.animation.PixelConverters

class TextSizeAnimInstance(private val endSize: Float): AttributesAnimInstance() {

    override fun getAnimator(viewToMove: View): Animator? {
        return if(viewToMove is TextView) {

            val startSize = PixelConverters.spToDp(viewToMove.context, viewToMove.textSize)
            ValueAnimator.ofFloat(startSize, endSize).apply {
                addUpdateListener {
                    val value = it.animatedValue as Float
                    viewToMove.textSize = value
                }
            }
        } else
            null
    }
}
