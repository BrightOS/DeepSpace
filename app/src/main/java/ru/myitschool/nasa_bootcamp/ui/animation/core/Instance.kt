package ru.myitschool.nasa_bootcamp.ui.animation.core


import android.view.Gravity
import android.view.View
import androidx.annotation.IntDef
import ru.myitschool.nasa_bootcamp.ui.animation.SpaceAnimation
import ru.myitschool.nasa_bootcamp.ui.animation.core.position.*
import ru.myitschool.nasa_bootcamp.ui.animation.core.scale.ScaleAnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.core.scale.ScaleAnimInstanceHeight
import ru.myitschool.nasa_bootcamp.ui.animation.core.scale.ScaleAnimInstanceValues
import ru.myitschool.nasa_bootcamp.ui.animation.core.scale.ScaleAnimInstanceWidth


class Instance(private val spaceAnimation: SpaceAnimation) {

    val instances = mutableListOf<AnimInstance>()

    internal val startActions: MutableList<() -> Unit> = mutableListOf()

    fun rightOf(
        view: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInsnance {
        return PositionAnimInsnanceRightOf(view).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }


    fun sameCenterAs(
        view: View,
        horizontal: Boolean = false,
        vertical: Boolean = false
    ): PositionAnimInsnance {
        return PositionAnimInsnanceSameCenterAs(view, horizontal, vertical).apply {
            instances.add(this)
        }
    }


    fun sameCenterVerticalAs(view: View): PositionAnimInsnance {
        return sameCenterAs(view, false, true).apply {
            instances.add(this)
        }
    }

    fun centerBetweenViews(
        view1: View,
        view2: View,
        horizontal: Boolean = false,
        vertical: Boolean = false
    ): PositionAnimInsnance {
        return PositionAnimInsnanceCenterBetweenViews(view1, view2, horizontal, vertical).apply {
            instances.add(this)
        }
    }

    fun topOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInsnance {
        return PositionAnimInsnanceTopOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun rightOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInsnance {
        return PositionAnimInsnanceRightOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun leftOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInsnance {
        return PositionAnimInsnanceLeftOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun alignLeft(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInsnance {
        return PositionAnimInsnanceAlignLeft(otherView).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun alignRight(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInsnance {
        return PositionAnimInsnanceAlignRight(otherView).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }


    fun scale(
        scaleX: Float,
        scaleY: Float,
        @GravityScaleHorizontalIntDef gravityHorizontal: Int,
        @GravityScaleVerticalIntDef gravityVertical: Int
    ): ScaleAnimInstance {
        return ScaleAnimInstanceValues(
            scaleX,
            scaleY,
            gravityHorizontal,
            gravityVertical
        ).apply {
            instances.add(this)
        }
    }

    fun scale(scaleX: Float, scaleY: Float): ScaleAnimInstance {
        return ScaleAnimInstanceValues(scaleX, scaleY, null, null).apply {
            instances.add(this)
        }
    }

    fun height(
        height: Int,
        @GravityScaleHorizontalIntDef horizontalGravity: Int? = null,
        @GravityScaleVerticalIntDef verticalGravity: Int? = null,
        keepRatio: Boolean = false,
        toDp: Boolean = false
    ): ScaleAnimInstance {
        return ScaleAnimInstanceHeight(height, horizontalGravity, verticalGravity).apply {
            this.keepRatio = keepRatio
            this.toDp = toDp
            instances.add(this)
        }
    }

    fun width(
        width: Int,
        @GravityScaleHorizontalIntDef horizontalGravity: Int? = null,
        @GravityScaleVerticalIntDef verticalGravity: Int? = null,
        keepRatio: Boolean = false,
        toDp: Boolean = false
    ): ScaleAnimInstance {
        return ScaleAnimInstanceWidth(width, horizontalGravity, verticalGravity).apply {
            instances.add(this)
            this.keepRatio = keepRatio
            this.toDp = toDp
        }
    }
}

@IntDef(value = [Gravity.TOP, Gravity.BOTTOM, Gravity.CENTER, Gravity.CENTER_VERTICAL])
annotation class GravityScaleVerticalIntDef

@IntDef(value = [Gravity.LEFT, Gravity.RIGHT, Gravity.END, Gravity.START, Gravity.CENTER, Gravity.CENTER_HORIZONTAL])
annotation class GravityScaleHorizontalIntDef