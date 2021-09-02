package ru.myitschool.nasa_bootcamp.ui.animation

import android.animation.*
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import java.util.concurrent.atomic.AtomicBoolean

class SpaceAnimation {

    private val instanceList: MutableList<ViewInstance> = mutableListOf()
    private var anyView: View? = null

    private var startDelay: Long = 5

    private val viewToMove: MutableList<View> = mutableListOf()
    private val viewRefresh: ViewRefresh = ViewRefresh()

    private var animatorSet: AnimatorSet? = null

    private val endListeners = mutableListOf<(SpaceAnimation) -> Unit>()
    private val startListeners = mutableListOf<(SpaceAnimation) -> Unit>()
    private val isPlaying = AtomicBoolean(false)

    private var interpolator: Interpolator? = null
    private var duration: Long = DEFAULT_DURATION

    private var firstAnim: SpaceAnimation? = null
    private var nextAnim: SpaceAnimation? = null

    fun animate(
        view: View,
        block: (Instance.() -> Unit)? = null
    ): ViewInstance {
        this.anyView = view

        val viewInstance = ViewInstance(this, view)
        instanceList.add(viewInstance)

        if (block != null) {
            return viewInstance.animateTo(block)
        } else {
            return viewInstance
        }
    }

    private fun update(): SpaceAnimation {
        if (animatorSet == null) {
            animatorSet = AnimatorSet()

            if (interpolator != null) {
                animatorSet!!.interpolator = interpolator
            }

            animatorSet!!.duration = duration

            val animatorList = mutableListOf<Animator>()

            val upcomingAnimations = mutableListOf<ViewInstance>()

            instanceList.forEach { anim ->
                viewToMove.add(anim.viewToMove)
                upcomingAnimations.add(anim)

                viewRefresh.setInstanceForView(anim.viewToMove, anim)
            }

            while (!upcomingAnimations.isEmpty()) {
                val iterator = upcomingAnimations.iterator()
                while (iterator.hasNext()) {
                    val viewExpectation = iterator.next()

                    viewExpectation.update(viewRefresh)
                    animatorList.addAll(viewExpectation.getAnimations())

                    val view = viewExpectation.viewToMove
                    viewToMove.remove(view)

                    iterator.remove()
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

    fun setStartDelay(startDelay: Long): SpaceAnimation {
        this.startDelay = startDelay
        return this
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

    fun isPlaying(): Boolean {
        return isPlaying.get()
    }

    fun now() {
        executeAfterDraw(anyView, Runnable { setPercent(1f) })
    }

    fun executeAfterDraw(view: View?, runnable: Runnable) {
        view?.postDelayed(runnable, Math.max(5, startDelay))
    }

    fun reset() {
        val objectAnimator = ObjectAnimator.ofFloat(this, "percent", 1f, 0f)
        objectAnimator.duration = duration
        if (interpolator != null) {
            objectAnimator.interpolator = interpolator
        }
        objectAnimator.start()
    }

    fun setInterpolator(interpolator: Interpolator): SpaceAnimation {
        this.interpolator = interpolator
        return this
    }

    fun setDuration(duration: Long): SpaceAnimation {
        this.duration = duration
        return this
    }

    fun withEndAction(listener: (SpaceAnimation) -> Unit): SpaceAnimation {
        this.endListeners.add(listener)
        return this
    }

    fun withStartAction(listener: (SpaceAnimation) -> Unit): SpaceAnimation {
        this.startListeners.add(listener)
        return this
    }

    fun thenCouldYou(
        duration: Long = 300L,
        interpolator: Interpolator = LinearInterpolator(),
        block: (SpaceAnimation.() -> Unit)
    ): SpaceAnimation {
        val pleaseAnim = SpaceAnimation()
        pleaseAnim.setDuration(duration)
        pleaseAnim.setInterpolator(interpolator)
        if (this.firstAnim == null) {
            this.firstAnim = this
        }
        pleaseAnim.firstAnim = this.firstAnim
        block.invoke(pleaseAnim)

        this.nextAnim = pleaseAnim

        withEndAction { PleaseAnim@ this.nextAnim?.startThen() }

        return pleaseAnim
    }

    private fun startThen() {
        executeAfterDraw(anyView, Runnable {
            update()
            animatorSet!!.start()
        })
    }

    fun start(): SpaceAnimation {
        if (this.firstAnim != null) {
            this.firstAnim?.startThen()
        } else {
            this.startThen()
        }
        return this
    }

    companion object {

        private val DEFAULT_DURATION = 300L

    }
}
