package ru.myitschool.nasa_bootcamp.ui.animation

import android.animation.*
import android.view.View
import android.view.animation.Interpolator
import java.util.concurrent.atomic.AtomicBoolean

//Анимайия
class SpaceAnimation {

    private val instanceList: MutableList<ViewInstance> = mutableListOf() //Список view для анимации
    private var anyView: View? = null

    private val viewToMove: MutableList<View> = mutableListOf()
    private val viewRefresh: ViewRefresh = ViewRefresh()

    private var animatorSet: AnimatorSet? = null

    private val endListeners = mutableListOf<(SpaceAnimation) -> Unit>()
    private val startListeners = mutableListOf<(SpaceAnimation) -> Unit>()
    private val isPlaying = AtomicBoolean(false)

    private var interpolator: Interpolator? = null
    private var duration: Long = 300L

    fun animate(view: View): ViewInstance {
        this.anyView = view

        val viewInstance = ViewInstance(this, view)
        instanceList.add(viewInstance)


        return viewInstance

    }

    private fun update(): SpaceAnimation {
        if (animatorSet == null) {
            animatorSet = AnimatorSet()
            animatorSet!!.interpolator = interpolator
            animatorSet!!.duration = duration

            val animatorList = mutableListOf<Animator>()

            val expectationsToCalculate = mutableListOf<ViewInstance>()

            instanceList.forEach { viewInstance ->
                viewInstance.changeDependencies()
                viewToMove.add(viewInstance.viewToAnimate)
                expectationsToCalculate.add(viewInstance)

                viewRefresh.setAnimatingForView(viewInstance.viewToAnimate, viewInstance)
            }

            while (!expectationsToCalculate.isEmpty()) {
                val iterator = expectationsToCalculate.iterator()
                while (iterator.hasNext()) {
                    val viewInstance = iterator.next()

                    if (!hasDependency(viewInstance)) {
                        viewInstance.change(viewRefresh)
                        animatorList.addAll(viewInstance.getAnimations())

                        val view = viewInstance.viewToAnimate
                        viewToMove.remove(view)

                        iterator.remove()
                    }
                }
            }

            animatorSet!!.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    isPlaying.set(false)
                    notifyListenerEnd()
                }

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    isPlaying.set(true)
                    notifyListenerStart()
                }

            })

            animatorSet!!.playTogether(animatorList)
        }
        return this
    }

    private fun notifyListenerStart() {
        startListeners.forEach { startListener ->
            startListener.invoke(this@SpaceAnimation)
        }
    }

    private fun notifyListenerEnd() {
        endListeners.forEach { endListener ->
            endListener.invoke(this@SpaceAnimation)
        }
    }

    private fun hasDependency(viewInstance: ViewInstance): Boolean {
        val dependencies = viewInstance.getDependencies()
        if (!dependencies.isEmpty()) {
            for (view in viewToMove) {
                if (dependencies.contains(view)) {
                    return true
                }
            }
        }
        return false
    }

    fun setPercent(percent: Float) {
        update()
        animatorSet?.childAnimations?.let { anims ->
            anims.forEach { animator ->
                if (animator is ValueAnimator) {
                    animator.currentPlayTime = (percent * animator.getDuration()).toLong()
                }
            }
        }
    }


    fun setInterpolator(interpolator: Interpolator): SpaceAnimation {
        this.interpolator = interpolator
        return this
    }

    fun setDuration(duration: Long): SpaceAnimation {
        this.duration = duration
        return this
    }

}
