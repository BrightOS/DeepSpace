package ru.myitschool.deepspace.navigator.renderer

import ru.myitschool.deepspace.maths.matrix.Vector3D
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10

class TexCoordBuffer : AbstractBuffer() {
    private lateinit var intBuffer: IntBuffer

    override var vertexCount: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
            if (vertexEmpty()) return

            val bb = ByteBuffer.allocateDirect(4 * 2 * vertexCount)
            bb.order(ByteOrder.nativeOrder())
            val ib = bb.asIntBuffer()
            ib.position(0)
            intBuffer = ib
        }

    override fun set(gl: GL10) {
        if (vertexEmpty()) return

        intBuffer.position(0)
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, intBuffer)
    }

    fun addTexCoords(u: Float, v: Float) {
        intBuffer.put((65536f * u).toInt())
        intBuffer.put((65536f * v).toInt())
    }
}


class VertexBuffer : AbstractBuffer() {

    private lateinit var bufInd: IntBuffer

    override var vertexCount: Int = 0
        set(value) {
            field = value

            if (vertexEmpty()) return

            val bb = ByteBuffer.allocateDirect(4 * 3 * vertexCount)
            bb.order(ByteOrder.nativeOrder())
            val intBuffer = bb.asIntBuffer()
            intBuffer.position(0)
            bufInd = intBuffer
        }

    fun addPoint(p: Vector3D) {
        addPoint(p.x, p.y, p.z)
    }

    fun addPoint(x: Double, y: Double, z: Double) {
        bufInd.apply {
            put((65536f * x).toInt())
            put((65536f * y).toInt())
            put((65536f * z).toInt())
        }
    }

    override fun set(gl: GL10) {
        if (vertexEmpty()) return

        bufInd.position(0)

        gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufInd)
    }
}

class IndexBuffer : AbstractBuffer() {
    override var vertexCount: Int = 0
        set(value) {
            field = value
            val bb = ByteBuffer.allocateDirect(2 * vertexCount)
            indexBuffer = bb.order(ByteOrder.nativeOrder()).asShortBuffer()
        }

    private var indexBuffer: ShortBuffer? = null

    override fun set(gl: GL10) {
        if (vertexEmpty()) return
        indexBuffer!!.position(0)
        gl.glDrawElements(GL10.GL_TRIANGLES, vertexCount, GL10.GL_UNSIGNED_SHORT, indexBuffer)
    }

    fun addIndex(index: Short) {
        indexBuffer!!.put(index)
    }
}

class ColorBuffer : AbstractBuffer() {
    internal lateinit var colorBuffer: IntBuffer

    override var vertexCount: Int = 0
        set(value) {
            field = value
            setupBuffer()
        }

    override fun set(gl: GL10) {
        if (vertexEmpty()) return
        colorBuffer.position(0)
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorBuffer)
    }

    private fun setupBuffer() {
        if (vertexEmpty()) return
        val byteBuffer = ByteBuffer.allocateDirect(4 * vertexCount)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intBuffer = byteBuffer.asIntBuffer()
        intBuffer.position(0)
        colorBuffer = intBuffer
    }
}

abstract class AbstractBuffer {
    abstract var vertexCount: Int
    abstract fun set(gl: GL10)

    fun vertexEmpty(): Boolean {
        return vertexCount == 0
    }
}
