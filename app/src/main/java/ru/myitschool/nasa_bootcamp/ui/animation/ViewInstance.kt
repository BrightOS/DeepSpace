package ru.myitschool.nasa_bootcamp.ui.animation

import android.animation.Animator
import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.core.AnimInstance
import ru.myitschool.nasa_bootcamp.ui.animation.core.Instance
import ru.myitschool.nasa_bootcamp.ui.animation.core.position.AnimPosition
import ru.myitschool.nasa_bootcamp.ui.animation.core.scale.AnimScale

class ViewInstance internal constructor(
    private val animation: SpaceAnimation,//анимация
    internal val viewToAnimate: View //view для анимации
) {
    private val dependencies: MutableList<View> = mutableListOf()
    private val animations: MutableList<Animator> = mutableListOf()
    internal val animInstances: MutableList<AnimInstance> = mutableListOf()

    private var willHasScaleX: Float? = null
    private var willHasScaleY: Float? = null

    private var willHasPositionX: Float? = null
    private var willHasPositionY: Float? = null

    private fun changePosition(viewRefresh: ViewRefresh) {
        val manager = AnimPosition(animInstances, viewToAnimate, viewRefresh)
        manager.change()
        willHasPositionX = manager.getPositionX();
        willHasPositionY = manager.getPositionY();
        animations.addAll(manager.getAnimators())
    }

    private fun changeScale(viewRefresh: ViewRefresh) {
        val manager = AnimScale(animInstances, viewToAnimate, viewRefresh)
        manager.update()
        willHasScaleX = manager.scaleX
        willHasScaleY = manager.scaleY
        animations.addAll(manager.animators)
    }


    internal fun change(viewRefresh: ViewRefresh) {
        changeScale(viewRefresh)
        changePosition(viewRefresh)
    }

    internal fun getAnimations(): List<Animator> {
        return animations
    }

    internal fun changeDependencies(): List<View> {
        dependencies.clear()
        for (animExpectation in animInstances) {
            dependencies.addAll(animExpectation.viewsDependencies)
        }
        return dependencies
    }

    internal fun getDependencies(): List<View> {
        return dependencies
    }

    internal fun getWillHasScaleX(): Float {
        return if (willHasScaleX != null) {
            willHasScaleX!!
        } else
            1f
    }

    internal fun getWillHasScaleY(): Float {
        return if (willHasScaleY != null) {
            willHasScaleY!!
        } else {
            1f
        }
    }

    internal fun getFuturePositionX(): Float? {
        return willHasPositionX
    }

    internal fun getFuturePositionY(): Float? {
        return willHasPositionY
    }

    infix fun animateInto(block: Instance.() -> Unit): ViewInstance {
        val instances = Instance(this.animation)
        block.invoke(instances)
        this.animInstances.addAll(instances.instances)
        return this
    }
}
