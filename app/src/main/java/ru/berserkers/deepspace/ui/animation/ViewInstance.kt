package ru.berserkers.deepspace.ui.animation

import android.animation.Animator
import android.view.View
import ru.berserkers.deepspace.ui.animation.alpha.AnimAlpha
import ru.berserkers.deepspace.ui.animation.attributes.AnimAttributes
import ru.berserkers.deepspace.ui.animation.position.AnimPosition
import ru.berserkers.deepspace.ui.animation.scale.AnimScale


class ViewInstance internal constructor(private val updateAnim: SpaceAnimation, internal val viewToMove: View) {
    private val animations: MutableList<Animator> = mutableListOf()
    private val animInstances: MutableList<AnimInstance> = mutableListOf()

    private var willHasScaleX: Float? = null
    private var willHasScaleY: Float? = null
    private var willHasPositionX: Float? = null
    private var willHasPositionY: Float? = null
    

    private fun changePosition(viewRefresh: ViewRefresh) {
        val manager = AnimPosition(animInstances, viewToMove, viewRefresh)
        manager.change()
        willHasPositionX = manager.getPositionX();
        willHasPositionY = manager.getPositionY();
        animations.addAll(manager.getAnimators())
    }

    private fun changeScale(viewRefresh: ViewRefresh) {
        val manager = AnimScale(animInstances, viewToMove, viewRefresh)
        manager.update()
        willHasScaleX = manager.scaleX
        willHasScaleY = manager.scaleY
        animations.addAll(manager.animators)
    }

    private fun updateAlpha(viewRefresh: ViewRefresh) {
        val manager = AnimAlpha(animInstances, viewToMove, viewRefresh)
        manager.change()
        animations.addAll(manager.getAnimators())
    }

    private fun updatePaddings(viewRefresh: ViewRefresh) {
        val manager = AnimAttributes(animInstances, viewToMove, viewRefresh)
        manager.change()
        animations.addAll(manager.getAnimators())
    }

    internal fun start(): SpaceAnimation {
        return updateAnim.start()
    }

    internal fun setPercent(percent: Float) {
        updateAnim.setPercent(percent)
    }

    internal fun update(viewRefresh: ViewRefresh) {
        changeScale(viewRefresh)
        changePosition(viewRefresh)
        updateAlpha(viewRefresh)
        updatePaddings(viewRefresh)
    }

    internal fun getAnimations(): List<Animator> {
        return animations
    }

    internal fun getWillHasScaleX(): Float {
        return if (willHasScaleX != null) willHasScaleX!! else 1f
    }

    internal fun getWillHasScaleY(): Float {
        return if (willHasScaleY != null) willHasScaleY!! else 1f
    }

    internal fun getFuturPositionX(): Float? {
        return willHasPositionX
    }

    internal fun getFuturPositionY(): Float? {
        return willHasPositionY
    }

    infix fun animateTo(block: Instance.() -> Unit): ViewInstance {
        val anims = Instance()
        block.invoke(anims)
        this.animInstances.addAll(anims.instances)
        return this
    }
}
