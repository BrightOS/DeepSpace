package ru.myitschool.deepspace.navigator.renderer

import javax.microedition.khronos.opengles.GL10

abstract class Renderer(val layer: Int, private val manager: TextureManager) {

    abstract fun reload(gl: GL10, fullReload: Boolean)
    abstract fun drawInternal(gl: GL10)

    var refresher: OnUpdateListener? = null
    private var radius = 360.0

    fun setMaxRadiusOfView(radiusOfView: Double) {
        radius = radiusOfView
    }

    fun queueForReload(fullReload: Boolean) {
        refresher!!.runRefresh(this, fullReload)
    }

    protected fun textureManager(): TextureManager {
        return manager
    }

    interface OnUpdateListener {
        fun runRefresh(rom: Renderer, fullReload: Boolean)
    }
}