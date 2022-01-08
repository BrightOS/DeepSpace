package ru.berserkers.deepspace.ui.animation

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

fun animateIt(duration: Long = 300L, interpolator: Interpolator = LinearInterpolator(), block: (SpaceAnimation.() -> Unit)) : SpaceAnimation {
    val anim = SpaceAnimation()
    anim.setDuration(duration)
    anim.setInterpolator(interpolator)
    block.invoke(anim)
    return anim
}