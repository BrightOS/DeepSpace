package ru.myitschool.nasa_bootcamp.ui.animation.core.scale

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.ViewRefresh
import ru.myitschool.nasa_bootcamp.ui.animation.core.AnimInstance

class AnimScale(
    private val animInstances: List<AnimInstance>,
    private val viewToMove: View,
    private val viewRefresh: ViewRefresh
) {

    var scaleX: Float? = null
        private set
    var scaleY: Float? = null
        private set

    private var moveX: Float? = null
    private var moveY: Float? = null

    val animators: List<Animator>
        get() {
            val animations = mutableListOf<Animator>()

            viewToMove.let { viewToMove ->
                moveX?.let {
                    viewToMove.pivotX = it
                }
                moveY?.let {
                    viewToMove.pivotY = it
                }

                scaleX?.let {
                    animations.add(ObjectAnimator.ofFloat(viewToMove, View.SCALE_X, it))
                }

                scaleY?.let {
                    animations.add(ObjectAnimator.ofFloat(viewToMove, View.SCALE_Y, it))
                }
            }

            return animations
        }

    fun update() {
        viewToMove.let { viewToMove ->

            animInstances.forEach { animExpectation ->
                if (animExpectation is ScaleAnimInstance) {

                    animExpectation.viewRefresh = viewRefresh

                    animExpectation.getChangedValueScaleX(viewToMove)?.let {
                        this.scaleX = it
                    }
                    animExpectation.getChangedValueScaleY(viewToMove)?.let {
                        this.scaleY = it
                    }

                    animExpectation.gravityHorizontal?.let { gravityHorizontal ->
                        when (gravityHorizontal) {
                            Gravity.LEFT, Gravity.START -> moveX = viewToMove.left.toFloat()
                            Gravity.RIGHT, Gravity.END -> moveX = viewToMove.right.toFloat()
                            Gravity.CENTER_HORIZONTAL, Gravity.CENTER -> moveX = viewToMove.left + viewToMove.width / 2f
                        }
                    }

                    animExpectation.gravityVertical?.let { gravityVertical ->
                        when (gravityVertical) {
                            Gravity.TOP -> moveY = viewToMove.top.toFloat()
                            Gravity.BOTTOM -> moveY = viewToMove.bottom.toFloat()
                            Gravity.CENTER_VERTICAL, Gravity.CENTER -> moveY = viewToMove.top + viewToMove.height / 2f
                        }
                    }

                }
            }
        }
    }
}
