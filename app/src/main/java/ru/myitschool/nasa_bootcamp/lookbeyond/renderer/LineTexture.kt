package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Color
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.*
import javax.microedition.khronos.opengles.GL10

class LineTexture(layer: Int, textureModule: TextureModule) :
    RendererManager(layer, textureModule) {
    private val vertexBuffer = VertexBuffer( )
    private val colorGl = ColorGl()
    private val texCoordBuffer = TexCoordGl( )
    private val indexGl = IndexGl()
    private var texture: Texture? = null

    fun updateObjects(lines: List<LineSource>) {
        var numLineSegments = 0
        for (lin in lines) {
            numLineSegments += lin.vertexGeocentric().size
        }

        val numVertices = 4 * numLineSegments
        val numIndices = 6 * numLineSegments


        val vb = vertexBuffer
        vb.reload(4 * numLineSegments)
        val cb = colorGl
        cb.reload(4 * numLineSegments)

        val tb = texCoordBuffer
        tb.reload(numVertices)
        val indexx = indexGl
        indexx.reload(numIndices)
        var vertexIndex: Short = 0

        for (lin in lines) {
            val coords: List<GeocentricCoord> = lin.vertexGeocentric()
            if (coords.size < 2) continue



            for (i in 0 until coords.size - 1) {
                val p1: Vector3D = coords[i]
                val p2: Vector3D = coords[i + 1]
                val difference = difference(p2, p1)
                val avg = sum(p1, p2)
                avg.scale(0.5)
                val v = normalized(crossV(difference, avg))
                v.scale(0.001202813 * lin.lineWidth)

                // Нижний левый угол
                vb.point(difference(p1, v))
                cb.intColorBuffer!!.put(Color.WHITE)
                tb.addCoords(0f, 1f)

                //Верхний левый угол
                vb.point(sum(p1, v))
                cb.intColorBuffer!!.put(Color.WHITE)
                tb.addCoords(0f, 0f)

                // Нижний левый угол
                vb.point(difference(p2, v))
                cb.intColorBuffer!!.put(Color.WHITE)
                tb.addCoords(1f, 1f)

                // Верхний левый угол
                vb.point(sum(p2, v))
                cb.intColorBuffer!!.put(Color.WHITE)
                tb.addCoords(1f, 0f)


                val bottomLeft = vertexIndex++
                val topLeft = vertexIndex++
                val bottomRight = vertexIndex++
                val topRight = vertexIndex++


                indexx.shortBufInd!!.put(bottomLeft)
                indexx.shortBufInd!!.put(topLeft)
                indexx.shortBufInd!!.put(bottomRight)


                indexx.shortBufInd!!.put(bottomRight)
                indexx.shortBufInd!!.put(topLeft)
                indexx.shortBufInd!!.put(topRight)

            }
        }
    }

    override fun reload(gl: GL10, fullReload: Boolean) {
        texture = texModule().loadResourceTexture(gl, R.drawable.line)
        colorGl.reload()
        texCoordBuffer.reload()
        indexGl.glBuffer.reload()
    }

    override fun drawTex(gl: GL10) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glEnable(GL10.GL_TEXTURE_2D)
        texture!!.create(gl)
        gl.glEnable(GL10.GL_CULL_FACE)
        gl.glFrontFace(GL10.GL_CW)
        gl.glCullFace(GL10.GL_BACK)

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE.toFloat())
        vertexBuffer.load(gl)

        colorGl.intColorBuffer!!.position(0)
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorGl.intColorBuffer)

        texCoordBuffer.load(gl)
        indexGl.draw(gl, GL10.GL_TRIANGLES)

        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
    }
}