package ru.berserkers.deepspace.navigator.renderer

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import java.util.*
import javax.microedition.khronos.opengles.GL10

interface AbstractTexture {
    fun bind(gl: GL10)
    fun delete(gl: GL10)
}

class TextureManager(private val res: Resources) {
    private val resourceIdToTextureMap: MutableMap<Int, AbstractTextureImpl> = HashMap()
    private val allTextures = ArrayList<AbstractTextureImpl>()

    fun createTexture(gl: GL10): AbstractTexture {
        return gl.create()
    }

    fun getTextureFromResource(gl: GL10, resourceID: Int): AbstractTexture {
        val texData = resourceIdToTextureMap[resourceID]
        if (texData != null) return texData
        val tex = gl.createTextureFromResource( resourceID)
        resourceIdToTextureMap[resourceID] = tex
        return tex
    }

    fun reset() {
        resourceIdToTextureMap.clear()
        allTextures.clear()
    }

    private fun GL10.createTextureFromResource( resourceID: Int): AbstractTextureImpl {
        val currentTexture = this.create()
        val bmp = BitmapFactory.decodeResource(res, resourceID, BitmapFactory.Options())
        currentTexture.bind(this)

        this.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
        this.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0)
        bmp.recycle()
        return currentTexture
    }

    private fun GL10.create(): AbstractTextureImpl {
        val textureId = IntArray(1)
        this.glGenTextures(1, textureId, 0)
        val tex = AbstractTextureImpl(textureId[0])
        allTextures.add(tex)
        return tex
    }
}
