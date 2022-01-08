package ru.berserkers.deepspace.navigator.touch

import android.content.Context
import ru.berserkers.deepspace.navigator.managers.Managers
import ru.berserkers.deepspace.navigator.maths.RADIANS_TO_DEGREES
import ru.berserkers.deepspace.navigator.touch.ZoomDetector.OnZoomListener

class Zoomer(private val managers: Managers, context: Context) :
    OnZoomListener {
    private val size: Double

    override fun onDrag(xPixels: Double, yPixels: Double): Boolean {
        return true
    }

    override fun onRotate(radians: Double): Boolean {
         return true
    }

    override fun onStretch(ratio: Double): Boolean {
        managers.zoomBy(1.0 / ratio)
        return true
    }

    init {
        val metrics = context.resources.displayMetrics
        val screenLongSize = metrics.heightPixels

        size = screenLongSize * RADIANS_TO_DEGREES
    }
}