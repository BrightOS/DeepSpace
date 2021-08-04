package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.BitmapFactory
import java.util.*
import javax.microedition.khronos.opengles.GL10


interface Texture {
    fun create(gl: GL10?)
    fun delete(gl: GL10?)
}


class TextureModule {
    private val textures = ArrayList<TextureImpl>()


    fun createTexture(gl: GL10): Texture {
        return createTex(gl)
    }

    fun loadResourceTexture(gl: GL10, res: Int): Texture {
        return createTextureFromResource(gl, res)
    }

    fun reload() {
        textures.clear()
    }

    private class TextureImpl(val id: Int) : Texture {
        override fun create(gl: GL10?) {
            gl!!.glBindTexture(GL10.GL_TEXTURE_2D, id)
        }

        override fun delete(gl: GL10?) {
            gl!!.glDeleteTextures(1, intArrayOf(id), 0)
        }
    }

    private fun createTextureFromResource(gl: GL10, res: Int): TextureImpl {
        val tex = createTex(gl)
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        tex.create(gl)

        gl.glTexParameterf(
            GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_WRAP_S,
            GL10.GL_CLAMP_TO_EDGE.toFloat()
        )

        gl.glTexParameterf(
            GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_WRAP_T,
            GL10.GL_CLAMP_TO_EDGE.toFloat()
        )
        return tex
    }

    private fun createTex(gl: GL10): TextureImpl {
        val idTexture = IntArray(1)
        gl.glGenTextures(1, idTexture, 0)
        val tex = TextureImpl(idTexture[0])
        textures.add(tex)
        return tex
    }
}