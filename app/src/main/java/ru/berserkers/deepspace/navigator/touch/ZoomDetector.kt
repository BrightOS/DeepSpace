package ru.berserkers.deepspace.navigator.touch

import android.view.MotionEvent
import ru.berserkers.deepspace.navigator.maths.RADIANS_TO_DEGREES
import kotlin.math.atan2
import kotlin.math.sqrt

class ZoomDetector(private val listener: OnZoomListener) {

    interface OnZoomListener {
        fun onDrag(xPixels: Double, yPixels: Double): Boolean
        fun onStretch(ratio: Double): Boolean
        fun onRotate(radians: Double): Boolean
    }

    private enum class State {
        READY, DRAGGING, STRETCH
    }

    private var state = State.READY
    private var lastFirstX = 0.0
    private var lastFirstY = 0.0
    private var lastSecondX = 0.0
    private var lastSecondY = 0.0

    private fun updateLast(event: MotionEvent) {
        lastFirstX = event.getX(0).toDouble()
        lastFirstY = event.getY(0).toDouble()
        lastSecondX = event.getX(1).toDouble()
        lastSecondY = event.getY(1).toDouble()
    }

    private fun updateLast(firstX: Double, firstY: Double, secondX: Double, secondY: Double) {
        lastFirstX = firstX
        lastFirstY = firstY
        lastSecondX = secondX
        lastSecondY = secondY
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        val actionCode = event.action and MotionEvent.ACTION_MASK

        if (actionCode == MotionEvent.ACTION_DOWN || state == State.READY) {
            return onActionDown(event)
        }
        if (actionCode == MotionEvent.ACTION_MOVE && state == State.DRAGGING) {
            onActionMove(event)
            return true
        }
        if (actionCode == MotionEvent.ACTION_MOVE && state == State.STRETCH) {
            if (onDraggingMove(event)) return false
            return true
        }
        if (actionCode == MotionEvent.ACTION_UP && state != State.READY) {
            state = State.READY
            return true
        }
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN && state == State.DRAGGING) {
            if (onBaseDragging(event)) return false
            return true
        }
        if (actionCode == MotionEvent.ACTION_POINTER_UP && state == State.STRETCH) {
            state = State.READY
            return true
        }
        return false
    }

    private fun onBaseDragging(event: MotionEvent): Boolean {
        state = State.STRETCH
        updateLast(event)
        return false
    }

    private fun onDraggingMove(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount
        if (pointerCount != 2) return true

        val firstX = event.getX(0).toDouble()
        val firstY = event.getY(0).toDouble()
        val secondX = event.getX(1).toDouble()
        val secondY = event.getY(1).toDouble()

        listener.onDrag(
            (firstX - lastFirstX + secondX - lastSecondX) / 2,
            (firstY - lastFirstY + secondY - lastSecondY) / 2
        )

        val vectorLastX = lastFirstX - lastSecondX
        val vectorLastY = lastFirstY - lastSecondY
        val vectorCurrentX = firstX - secondX
        val vectorCurrentY = firstY - secondY

        fun sumupCoords(x: Double, y: Double): Double {
            return (x * x + y * y)
        }

        listener.onStretch(sqrt(sumupCoords(vectorCurrentX, vectorCurrentY) / sumupCoords(vectorLastX, vectorLastY)))
        val delta = atan2(vectorCurrentX, vectorCurrentY) - atan2(vectorLastX, vectorLastY)

        listener.onRotate(delta * RADIANS_TO_DEGREES)

        updateLast(firstX, firstY, secondX, secondY)

        return false
    }

    private fun onActionDown(event: MotionEvent): Boolean {
        state = State.DRAGGING
        updateLast(event.x.toDouble(), event.y.toDouble(), lastSecondX, lastSecondY)
        return true
    }

    private fun onActionMove(event: MotionEvent) {
        val x = event.x.toDouble()
        val y = event.y.toDouble()
        listener.onDrag(x - lastFirstX, y - lastFirstY)

        updateLast(x, y, lastSecondX, lastSecondY)
    }
}
