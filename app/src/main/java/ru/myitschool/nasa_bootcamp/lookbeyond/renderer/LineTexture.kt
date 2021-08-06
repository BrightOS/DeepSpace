package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Color
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.*
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineRes
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.tan


class LineTexture(layer: Int, textureModule: TextureModule?) :
    RendererManager(layer, textureModule!!) {
    private val vertexBuffer = VertexBuffer()
    private val colorGl = ColorGl()
    private val texCoordBuffer = TexCoordGl()
    private val indexGl = IndexGl()
    private var texture: Texture? = null

    fun updateObjects(lines: List<LineRes>) {

        var numLineSegments = 0
        for (l in lines) {
            numLineSegments += l.vertexGeocentric.size - 1
        }

        val numVertices = 4 * numLineSegments
        val numIndices = 6 * numLineSegments
        val vb = vertexBuffer
        vb.reload(4 * numLineSegments)
        val cb = colorGl
        cb.reload(4 * numLineSegments)
        val tb = texCoordBuffer
        tb.reset(numVertices)
        val ib = indexGl
        ib.reset(numIndices)

        val fovyInRadians = 60 * PI / 180.0
        val sizeFactor = tan(fovyInRadians * 0.5) / 480

        var vertexIndex: Short = 0
        for (l in lines) {
            val coords = l.vertexGeocentric
            if (coords.size < 2) continue

            for (i in 0 until coords.size - 1) {
                val p1: Vector3D = coords[i]
                val p2: Vector3D = coords[i + 1]
                val u = sumNormalToOpposite(p2, p1)

                val avg = sum(p1, p2)
                avg.scale(0.5)

                val v = normalized(crossProduct(u, avg))
                v.scale(sizeFactor * l.lineWidth)


                // Нижний левый угол
                vb.point(sumNormalToOpposite(p1, v))
                cb.intBuffer!!.put(Color.WHITE)
                tb.addTexCoords(0f, 1f)

                //Верзний левый угол
                vb.point(sum(p1, v))
                cb.intBuffer!!.put(Color.WHITE)
                tb.addTexCoords(0f, 0f)

                //Левый нижний угол
                vb.point(sumNormalToOpposite(p2, v))
                cb.intBuffer!!.put(Color.WHITE)
                tb.addTexCoords(1f, 1f)

                // Верхний левый угол
                vb.point(sum(p2, v))
                cb.intBuffer!!.put(Color.WHITE)
                tb.addTexCoords(1f, 0f)


                val bottomLeft = vertexIndex++
                val topLeft = vertexIndex++
                val bottomRight = vertexIndex++
                val topRight = vertexIndex++

                ib.shortBufInd!!.put(bottomLeft)
                ib.shortBufInd!!.put(topLeft)
                ib.shortBufInd!!.put(bottomRight)

                ib.shortBufInd!!.put(bottomRight)
                ib.shortBufInd!!.put(topLeft)
                ib.shortBufInd!!.put(topRight)
            }
        }
    }

    override fun reload(gl: GL10, fullReload: Boolean) {
        texture = textureManager().loadResourceTexture(gl, R.drawable.line)
        vertexBuffer.glBuffer.reload()
        colorGl.glBuffer.reload()
        texCoordBuffer.reload()
        indexGl.glBuffer.reload()
    }

    override fun renderTexture(gl: GL10) {
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

        colorGl.intBuffer!!.position(0)
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorGl.intBuffer)

        texCoordBuffer.load(gl)
        indexGl.draw(gl, GL10.GL_TRIANGLES)

        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
    }
 
}