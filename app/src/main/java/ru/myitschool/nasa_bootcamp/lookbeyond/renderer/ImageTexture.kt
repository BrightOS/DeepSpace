package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Bitmap
import android.opengl.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11


class ImageTexture(layer: Int, module: TextureModule) :
    RendererManager(layer, module) {

    private val _vertexBuffer = VertexBuffer()
    private val _texCoordGl = TexCoordGl()
    private var images = arrayOfNulls<Bitmap>(0)
    private var _textures = arrayOfNulls<Texture>(0)
    private var redTextures = arrayOfNulls<Texture>(0)

    fun updateObjects(imageRes: List<ImageRes>) {

        val numVertices = imageRes.size * 4
        val vertexBuffer = _vertexBuffer
        vertexBuffer.reload(numVertices)
        val texCoord = _texCoordGl
        texCoord.reload(numVertices)
        val images: Array<Bitmap?>

        images = arrayOfNulls(imageRes.size)


        for (i in imageRes.indices) {
            val `is` = imageRes[i]

            images[i] = `is`.image
        }


        for (i in imageRes.indices) {
            val imageSource = imageRes[i]
            val xyz = imageSource.location
            val px = xyz.x
            val py = xyz.y
            val pz = xyz.z

            val u = imageSource.horizontalCorner

            val ux = u[0]
            val uy = u[1]
            val uz = u[2]

            val v = imageSource.verticalCorner

            val vx = v[0]
            val vy = v[1]
            val vz = v[2]

            //снизу слева
            vertexBuffer.point(px - ux - vx, py - uy - vy, pz - uz - vz)
            texCoord.addCoords(0f, 1f)

            //сверху слева
            vertexBuffer.point(px - ux + vx, py - uy + vy, pz - uz + vz)
            texCoord.addCoords(0f, 0f)

            //снизу справа
            vertexBuffer.point(px + ux - vx, py + uy - vy, pz + uz - vz)
            texCoord.addCoords(1f, 1f)

            //сверху справа
            vertexBuffer.point(px + ux + vx, py + uy + vy, pz + uz + vz)
            texCoord.addCoords(1f, 0f)

        }
        this.images = images
    }

    override fun reload(gl: GL10, fullReload: Boolean) {
        val images = images

        val reloadBuffers = true
        val reloadImages = true

        _textures = arrayOfNulls(images.size)
        redTextures = arrayOfNulls(images.size)

        if (reloadBuffers) {
            _vertexBuffer.reload()
            _texCoordGl.reload()
        }
        if (reloadImages) {
            for (i in _textures.indices) {
                if (_textures[i] != null) {
                    _textures[i]!!.delete(gl)
                    redTextures[i]!!.delete(gl)
                }
                val bmp = this.images[i]

                _textures[i] = texModule().createTexture(gl)
                _textures[i]!!.create(gl)

                gl.glTexParameterf(
                    GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_LINEAR.toFloat()
                )
                gl.glTexParameterf(
                    GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR.toFloat()
                )
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
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0)

                redTextures[i] = texModule().createTexture(gl)
                redTextures[i]!!.create(gl)

                gl.glTexParameterf(
                    GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_LINEAR.toFloat()
                )
                gl.glTexParameterf(
                    GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR.toFloat()
                )
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

                val bb = ByteBuffer.allocateDirect(bmp!!.height * bmp.width * 4)
                bb.order(ByteOrder.nativeOrder())
                val ib = bb.asIntBuffer()

                for (y in 0 until bmp.height) for (x in 0 until bmp.width) {
                    ib.put(bmp.getPixel(x, y))
                }
                ib.position(0)
                bb.position(0)

                gl.glTexImage2D(
                    GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, bmp.width, bmp.height,
                    0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb
                )
            }
        }
    }

    override fun drawTex(gl: GL10) {

        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY)

        _vertexBuffer.load(gl)
        _texCoordGl.load(gl)
        val textures = _textures

        for (i in textures.indices) {

            gl.glEnable(GL10.GL_ALPHA_TEST)
            gl.glAlphaFunc(GL10.GL_GREATER, 0.5f)


            textures[i]!!.create(gl)

            (gl as GL11).glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4 * i, 4)

            gl.glDisable(GL10.GL_ALPHA_TEST)

        }
        gl.glDisable(GL10.GL_TEXTURE_2D)
    }

}