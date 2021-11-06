package ru.myitschool.deepspace.navigator.renderer

import javax.microedition.khronos.opengles.GL10

class AbstractTextureImpl(val id: Int) : AbstractTexture {
    override fun bind(gl: GL10) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, id)
    }

    override fun delete(gl: GL10) {
        gl.glDeleteTextures(1, intArrayOf(id), 0)
    }
}