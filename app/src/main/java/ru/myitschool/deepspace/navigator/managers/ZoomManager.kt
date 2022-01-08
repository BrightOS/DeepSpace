package ru.myitschool.deepspace.navigator.managers

import ru.myitschool.deepspace.navigator.pointing.VectorPointing
import kotlin.math.min

class ZoomManager : AbstractManager {
    override var pointing: VectorPointing? = null

    fun zoomBy(ratio: Double) {
        var zoomDegrees = pointing!!.viewDegree
        zoomDegrees = min(zoomDegrees * ratio, MAX_ZOOM_OUT)
        setFieldOfView(zoomDegrees)
    }

    private fun setFieldOfView(zoomDegrees: Double) {
        pointing!!.viewDegree = zoomDegrees
    }

    override fun start() {
    }

    override fun stop() {
    }

    companion object {
        val MAX_ZOOM_OUT = 90.0
    }
}