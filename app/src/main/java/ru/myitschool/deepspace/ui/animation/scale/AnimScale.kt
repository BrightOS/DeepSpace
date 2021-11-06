package ru.myitschool.deepspace.ui.animation.scale

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import ru.myitschool.deepspace.ui.animation.AnimInstance
import ru.myitschool.deepspace.ui.animation.ViewRefresh

class AnimScale(
    private val animInstances: List<AnimInstance>,
    private val viewToMove: View?,
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

            viewToMove?.let { viewToMove ->
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
        viewToMove?.let { viewToMove ->

            animInstances.forEach { anim ->
                if (anim is ScaleInstance) {

                    anim.viewRefresh = viewRefresh

                    anim.getChangedValueScaleX(viewToMove)?.let {
                        this.scaleX = it
                    }
                    anim.getChangedValueScaleY(viewToMove)?.let {
                        this.scaleY = it
                    }

                    anim.gravityHorizontal?.let { gravityHorizontal ->
                        when (gravityHorizontal) {
                            Gravity.LEFT, Gravity.START -> moveX = viewToMove.left.toFloat()
                            Gravity.RIGHT, Gravity.END -> moveX = viewToMove.right.toFloat()
                            Gravity.CENTER_HORIZONTAL, Gravity.CENTER -> moveX = viewToMove.left + viewToMove.width / 2f
                        }
                    }

                    anim.gravityVertical?.let { gravityVertical ->
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
