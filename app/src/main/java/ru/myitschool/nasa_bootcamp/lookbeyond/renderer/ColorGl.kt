package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL11

class ColorGl {
    fun reload(numVertices: Int) {
        verticesCount = numVertices
        val byteBuffer = ByteBuffer.allocateDirect(4 * verticesCount)
        intColorBuffer = byteBuffer.asIntBuffer()
    }

    fun reload() {
        glBuffer.reload()
    }

    internal var intColorBuffer: IntBuffer? = null
    private var verticesCount = 0
    private val glBuffer = GLBuffer(GL11.GL_ARRAY_BUFFER)
}


class GLBuffer internal constructor(private val type: Int) {
    private var buffer: Buffer? = null
    private var bufferSize = 0
    private var bufferIndex = -1

    fun create(gl: GL11, buffer: Buffer, bufferSize: Int) {
        this.buffer = buffer
        this.bufferSize = bufferSize
        gl.glBindBuffer(type, bufferIndex)
        gl.glBufferData(type, bufferSize, buffer, GL11.GL_STATIC_DRAW)
        gl.glBindBuffer(type, bufferIndex)
    }

    fun reload() {
        buffer = null
        bufferSize = 0
        bufferIndex = -1
    }
}

