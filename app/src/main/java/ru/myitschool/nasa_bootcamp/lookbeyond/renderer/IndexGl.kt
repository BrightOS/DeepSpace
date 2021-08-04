package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11

class IndexGl{

    fun reload(numVertices: Int) {
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

        val gl11 = gl as GL11
        glBuffer.create(gl11, shortBufInd!!, 2 * shortBufInd!!.capacity())
        gl11.glDrawElements(primitiveType, vertexCount, GL10.GL_UNSIGNED_SHORT, 0)
    }

    internal var shortBufInd: ShortBuffer? = null
    private var vertexCount = 0
    internal val glBuffer = GLBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER)

    init {
        vertexCount = 0
    }
}
