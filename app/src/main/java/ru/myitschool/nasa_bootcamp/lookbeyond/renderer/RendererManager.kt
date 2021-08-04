package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import javax.microedition.khronos.opengles.GL10


abstract class RendererManager(val layer: Int, private val textureModule: TextureModule)  {

    fun render(gl: GL10) {
        if (enabled && renderState!!.radiusOfView <= 360) {
            drawTex(gl)
        }
    }

    protected fun texModule(): TextureModule {
        return textureModule
    }

    abstract fun reload(gl: GL10, fullReload: Boolean)
    protected abstract fun drawTex(gl: GL10)
    private var enabled = true
    var renderState: RenderStateInterface? = null

}