package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Vector3D
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11

class TexCoordGl {

    fun reset(_vertexCount: Int) {
        val vertexCount = _vertexCount
        this.vertexCount = vertexCount
        regenerateBuffer()
    }

    fun reload() {
        glBuffer.reload()
    }

    fun addTexCoords(u: Float, v: Float) {
        coordBuffer!!.put((65536f * u).toInt())
        coordBuffer!!.put((65536f * v).toInt())
    }

    fun load(gl: GL10) {
        if (vertexCount == 0) {
            return
        }
        coordBuffer!!.position(0)
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, coordBuffer)
    }

    private fun regenerateBuffer() {
        if (vertexCount == 0) {
            return
        }
        val bb = ByteBuffer.allocateDirect(4 * 2 * vertexCount)
        bb.order(ByteOrder.nativeOrder())
        val ib = bb.asIntBuffer()
        ib.position(0)
        coordBuffer = ib
    }

    private var coordBuffer: IntBuffer? = null
    private var vertexCount = 0
    private val glBuffer = GLBuffer(GL11.GL_ARRAY_BUFFER)

    init {
        vertexCount = 0
    }
}

class VertexBuffer {

    fun reload(numVertices: Int) {
        vertexCount = numVertices
        rebuffer()
    }

    fun point(p: Vector3D) {
        point(p.x, p.y, p.z)
    }

    fun point(x: Double, y: Double, z: Double) {
        pos!!.put((65536f * x).toInt())
        pos!!.put((65536f * y).toInt())
        pos!!.put((65536f * z).toInt())
    }

    fun load(gl: GL10) {
        pos!!.position(0)
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, pos)
    }

    private fun rebuffer() {
        val bb = ByteBuffer.allocateDirect(4 * 3 * vertexCount)
        bb.order(ByteOrder.nativeOrder())
        val ib = bb.asIntBuffer()
        ib.position(0)
        pos = ib
    }

    private var pos: IntBuffer? = null
    private var vertexCount = 0
    internal val glBuffer = GLBuffer(GL11.GL_ARRAY_BUFFER)

    init {
        vertexCount = 0
    }
}

class IndexGl {
    fun reset(numVertices: Int) {
        vertexCount = numVertices

        if (vertexCount == 0) {
            return
        }
        val bb = ByteBuffer.allocateDirect(2 * vertexCount)
        bb.order(ByteOrder.nativeOrder())
        val ib = bb.asShortBuffer()
        ib.position(0)
        shortBufInd = ib
    }


    fun draw(gl: GL10, primitiveType: Int) {
        if (vertexCount == 0) {
            return
        }
        shortBufInd!!.position(0)

        gl.glDrawElements(primitiveType, vertexCount, GL10.GL_UNSIGNED_SHORT, shortBufInd)


    }

    internal var shortBufInd: ShortBuffer? = null
    private var vertexCount = 0
    internal val glBuffer = GLBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER)

    init {
        vertexCount = 0
    }
}