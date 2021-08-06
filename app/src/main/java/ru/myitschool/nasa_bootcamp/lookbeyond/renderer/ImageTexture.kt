package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Bitmap
import android.opengl.GLUtils
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageRes
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11


class ImageTexture(layer: Int, module: TextureModule?) :
    RendererManager(layer, module!!) {
    private val _vertexBuffer = VertexBuffer()
    private val _texCoordBuffer = TexCoordGl()
    private var _images = arrayOfNulls<Image>(0)
    private var _textures = arrayOfNulls<Texture>(0)
    private var _redTextures = arrayOfNulls<Texture>(0)

    fun updateObjects(imageRes: List<ImageRes> ) {
        val numVertices = imageRes.size * 4
        val vertexBuffer = _vertexBuffer
        vertexBuffer.reload(numVertices)
        val texCoordBuffer = _texCoordBuffer
        texCoordBuffer.reset(numVertices)
        val images: Array<Image?> = arrayOfNulls(imageRes.size)


            for (i in imageRes.indices) {
                val img = imageRes[i]
                images[i] = Image()
                images[i]!!.bitmap = img.image
            }




        for (i in imageRes.indices) {
            val imageSource = imageRes[i]
            val xyz = imageSource.location
            val px = xyz!!.x
            val py = xyz.y
            val pz = xyz.z

            val up = imageSource.horizontalCorner

            val ux = up!![0]
            val uy = up[1]
            val uz = up[2]

            val verticalCorner = imageSource.verticalCorner

            val vx = verticalCorner!![0]
            val vy = verticalCorner[1]
            val vz = verticalCorner[2]

            //снизу слева
            vertexBuffer.point(px - ux - vx, py - uy - vy, pz - uz - vz)
            texCoordBuffer.addTexCoords(0f, 1f)

            //сверху слева
            vertexBuffer.point(px - ux + vx, py - uy + vy, pz - uz + vz)
            texCoordBuffer.addTexCoords(0f, 0f)

            //снизу справа
            vertexBuffer.point(px + ux - vx, py + uy - vy, pz + uz - vz)
            texCoordBuffer.addTexCoords(1f, 1f)

            //сверху справа
            vertexBuffer.point(px + ux + vx, py + uy + vy, pz + uz + vz)
            texCoordBuffer.addTexCoords(1f, 0f)
        }


        _images = images
        queueForReload(false)
    }

    override fun reload(gl: GL10, fullReload: Boolean) {
        val images = _images

        val reloadBuffers = true
        val reloadImages = true
        _textures = arrayOfNulls(images.size)
        _redTextures = arrayOfNulls(images.size)

        if (reloadBuffers) {
            _vertexBuffer.glBuffer.reload()
            _texCoordBuffer.reload()
        }
        if (reloadImages) {
            for (i in _textures.indices) {
                if (_textures[i] != null) {
                    _textures[i]!!.delete(gl)
                    _redTextures[i]!!.delete(gl)
                }
                val bmp = _images[i]!!.bitmap

                _textures[i] = textureManager().createTexture(gl)
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


                _redTextures[i] = textureManager().createTexture(gl)
                _redTextures[i]!!.create(gl)

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

    override fun renderTexture(gl: GL10) {
        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY)

        _vertexBuffer.load(gl)
        _texCoordBuffer.load(gl)
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

    private class Image {
        var bitmap: Bitmap? = null
    }
}