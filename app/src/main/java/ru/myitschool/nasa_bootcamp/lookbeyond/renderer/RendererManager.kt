package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import javax.microedition.khronos.opengles.GL10


abstract class RendererManager(val layer: Int, private val mTextureModule: TextureModule)  {

    fun draw(gl: GL10) {
        if ( renderingValues!!.radiusOfView <= maxRadiusOfView) {
            renderTexture(gl)
        }
    }

    interface ReloadListener {
        fun queueForReload(rom: RendererManager, fullReload: Boolean)
    }

    fun setReloadListener(listener: ReloadListener?) {
        this.listener = listener
    }

     fun queueForReload(fullReload: Boolean) {
        listener!!.queueForReload(this, fullReload)
    }


    protected fun textureManager(): TextureModule {
        return mTextureModule
    }

    abstract fun reload(gl: GL10, fullReload: Boolean)
    protected abstract fun renderTexture(gl: GL10)

    var renderingValues: RenderingValuesInterface? = null
    private var listener: ReloadListener? = null

    private var maxRadiusOfView = 360.0
    private var index = 0

    companion object {

        private var _index = 0
    }

    init {
        synchronized(RendererManager::class.java) {
            index = _index++
        }
    }
}
