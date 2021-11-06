package ru.myitschool.deepspace.ui.animation

import android.view.Gravity
import android.view.View
import androidx.annotation.IntDef
import ru.myitschool.deepspace.ui.animation.alpha.AlphaInstance
import ru.myitschool.deepspace.ui.animation.alpha.AlphaInstanceValue
import ru.myitschool.deepspace.ui.animation.attributes.AttributesAnimInstance
import ru.myitschool.deepspace.ui.animation.attributes.TextColorAnimInstance
import ru.myitschool.deepspace.ui.animation.attributes.TextSizeAnimInstance
import ru.myitschool.deepspace.ui.animation.margins.Margin
import ru.myitschool.deepspace.ui.animation.margins.MarginSetAnimInstance
import ru.myitschool.deepspace.ui.animation.paddings.Padding
import ru.myitschool.deepspace.ui.animation.paddings.PaddingSetAnimInstance
import ru.myitschool.deepspace.ui.animation.position.*
import ru.myitschool.deepspace.ui.animation.scale.ScaleInstance
import ru.myitschool.deepspace.ui.animation.scale.ScaleInstanceHeight
import ru.myitschool.deepspace.ui.animation.scale.ScaleInstanceValues
import ru.myitschool.deepspace.ui.animation.scale.ScaleInstanceWidth

class Instance() {

    val instances = mutableListOf<AnimInstance>()

    internal val startActions: MutableList<() -> Unit> = mutableListOf()

    @IntDef(value = [Gravity.LEFT, Gravity.RIGHT, Gravity.END, Gravity.START, Gravity.CENTER, Gravity.CENTER_HORIZONTAL])
    annotation class GravityScaleHorizontalIntDef

    @IntDef(value = [Gravity.TOP, Gravity.BOTTOM, Gravity.CENTER, Gravity.CENTER_VERTICAL])
    annotation class GravityScaleVerticalIntDef


    fun rightOf(
        view: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceRightOf(view).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun leftOf(
        view: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceLeftOf(view).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }


    fun sameCenterAs(
        view: View,
        horizontal: Boolean = false,
        vertical: Boolean = false
    ): PositionAnimInstance {
        return PositionAnimInstanceSameCenterAs(view, horizontal, vertical).apply {
            instances.add(this)
        }
    }

    fun sameCenterHorizontalAs(view: View): PositionAnimInstance {
        return sameCenterAs(view, horizontal = true, vertical = false).apply {
            instances.add(this)
        }
    }

    fun sameCenterVerticalAs(view: View): PositionAnimInstance {
        return sameCenterAs(view, false, true).apply {
            instances.add(this)
        }
    }

    fun centerInParent(horizontal: Boolean, vertical: Boolean): PositionAnimInstance {
        return PositionAnimInstanceCenterInParent(horizontal, vertical).apply {
            instances.add(this)
        }
    }

    fun centerVerticalInParent(): PositionAnimInstance {
        return centerInParent(false, true).apply {
            instances.add(this)
        }
    }

    fun centerHorizontalInParent(): PositionAnimInstance {
        return centerInParent(true, false).apply {
            instances.add(this)
        }
    }


    fun centerBetweenViews(
        view1: View,
        view2: View,
        horizontal: Boolean = false,
        vertical: Boolean = false
    ): PositionAnimInstance {
        return PositionAnimInstanceCenterBetweenViews(view1, view2, horizontal, vertical).apply {
            instances.add(this)
        }
    }

    fun topOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInstance {
        return PositionAnimInstanceTopOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun rightOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInstance {
        return PositionAnimInstanceRightOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun bottomOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInstance {
        return PositionAnimInstanceBottomOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun leftOfItsParent(marginDp: Float? = null, margin: Float? = null): PositionAnimInstance {
        return PositionAnimInstanceLeftOfParent().apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }


    fun alignBottom(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceAlignBottom(otherView).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun alignTop(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceAlignTop(otherView).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun alignLeft(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceAlignLeft(otherView).apply {
            instances.add(this)
            this.marginDp = marginDp
            this.margin = margin
        }
    }

    fun alignRight(
        otherView: View,
        marginDp: Float? = null,
        margin: Float? = null
    ): PositionAnimInstance {
        return PositionAnimInstanceAlignRight(otherView).apply {
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
    ): ScaleInstance {
        return ScaleInstanceValues(
            scaleX,
            scaleY,
            gravityHorizontal,
            gravityVertical
        ).apply {
            instances.add(this)
        }
    }

    fun scale(scaleX: Float, scaleY: Float): ScaleInstance {
        return ScaleInstanceValues(scaleX, scaleY, null, null).apply {
            instances.add(this)
        }
    }

    fun height(
        height: Int,
        @GravityScaleHorizontalIntDef horizontalGravity: Int? = null,
        @GravityScaleVerticalIntDef verticalGravity: Int? = null,
        keepRatio: Boolean = false,
        toDp: Boolean = false
    ): ScaleInstance {
        return ScaleInstanceHeight(height, horizontalGravity, verticalGravity).apply {
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
    ): ScaleInstance {
        return ScaleInstanceWidth(width, horizontalGravity, verticalGravity).apply {
            instances.add(this)
            this.keepRatio = keepRatio
            this.toDp = toDp
        }
    }

    fun alpha(alpha: Float): AlphaInstance {
        return AlphaInstanceValue(alpha).apply {
            instances.add(this)
        }
    }

    fun visible(): AlphaInstance {
        return AlphaInstanceValue(1f).apply {
            instances.add(this)
        }
    }

    fun invisible(): AlphaInstance {
        return AlphaInstanceValue(0f).apply {
            instances.add(this)
        }
    }

    fun textColor(textColor: Int): AttributesAnimInstance {
        return TextColorAnimInstance(textColor).apply {
            instances.add(this)
        }
    }

    fun textSize(endSize: Float): AttributesAnimInstance {
        return TextSizeAnimInstance(endSize).apply {
            instances.add(this)
        }
    }


    fun marginTop(marginValue: Float): AttributesAnimInstance {
        return MarginSetAnimInstance(marginValue, Margin.TOP).apply {
            instances.add(this)
        }
    }

    fun marginBottom(marginValue: Float): AttributesAnimInstance {
        return MarginSetAnimInstance(marginValue, Margin.BOTTOM).apply {
            instances.add(this)
        }
    }

    fun marginRight(marginValue: Float): AttributesAnimInstance {
        return MarginSetAnimInstance(marginValue, Margin.RIGHT).apply {
            instances.add(this)
        }
    }

    fun marginLeft(marginValue: Float): AttributesAnimInstance {
        return MarginSetAnimInstance(marginValue, Margin.LEFT).apply {
            instances.add(this)
        }
    }

    fun paddingTop(paddingValue: Float): AttributesAnimInstance {
        return PaddingSetAnimInstance(paddingValue, Padding.TOP).apply {
            instances.add(this)
        }
    }

    fun paddingBottom(paddingValue: Float): AttributesAnimInstance {
        return PaddingSetAnimInstance(paddingValue, Padding.BOTTOM).apply {
            instances.add(this)
        }
    }

    fun paddingLeft(paddingValue: Float): AttributesAnimInstance {
        return PaddingSetAnimInstance(paddingValue, Padding.LEFT).apply {
            instances.add(this)
        }
    }

    fun paddingRight(paddingValue: Float): AttributesAnimInstance {
        return PaddingSetAnimInstance(paddingValue, Padding.RIGHT).apply {
            instances.add(this)
        }
    }
}
