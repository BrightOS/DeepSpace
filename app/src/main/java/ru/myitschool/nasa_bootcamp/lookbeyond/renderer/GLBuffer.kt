package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL11

class GLBuffer internal constructor(private val type: Int) {
    private var _buffer: Buffer? = null
    private var size = 0
    private var id = -1

    fun create(gl: GL11, buffer: Buffer, bufferSize: Int) {
        this._buffer = buffer
        this.size = bufferSize
        gl.glBindBuffer(type, id)
        gl.glBufferData(type, bufferSize, buffer, GL11.GL_STATIC_DRAW)
        gl.glBindBuffer(type, id)
    }

    fun reload() {
        _buffer = null
        size = 0
        id = -1
    }
}

class ColorGl {
    fun reload(numVertices: Int) {
        vertexCount = numVertices
        if (vertexCount == 0) {
            return
        }
        val bb = ByteBuffer.allocateDirect(4 * vertexCount)
        bb.order(ByteOrder.nativeOrder())
        val ib = bb.asIntBuffer()
        ib.position(0)
        intBuffer = ib
    }

    public var intBuffer: IntBuffer? = null
    private var vertexCount = 0
    internal val glBuffer = GLBuffer(GL11.GL_ARRAY_BUFFER)

    init {
        vertexCount = 0
    }
}