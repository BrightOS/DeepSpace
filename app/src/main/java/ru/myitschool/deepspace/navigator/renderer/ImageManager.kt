package ru.myitschool.deepspace.navigator.renderer

import android.graphics.Bitmap
import android.opengl.GLUtils
import ru.myitschool.deepspace.navigator.rendertype.ImageRun
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11

class ImageManager(layer: Int, manager: TextureManager) :
    Renderer(layer, manager) {

    private val vertexBuffer = VertexBuffer()
    private val coordBuffer = TexCoordBuffer()
    private var images = arrayOfNulls<Image>(0)
    private var textures = arrayOfNulls<AbstractTexture>(0)

    fun updateObjects(imageSources: List<ImageRun>) {

        val numVertices = imageSources.size * 4

        vertexBuffer.vertexCount = numVertices
        coordBuffer.vertexCount = numVertices

        val images: Array<Image?> = arrayOfNulls(imageSources.size)

        for (i in imageSources.indices) {
            val imageRun = imageSources[i]
            images[i] = Image()

            images[i]!!.useBlending = false
            images[i]!!.bitmap = imageRun.image
        }

        for (i in imageSources.indices) {
            val imgSources = imageSources[i]

            val xyz = imgSources.location
            val h = imgSources.horizontalCorner
            val v = imgSources.verticalCorner

            with(vertexBuffer) {
                addPoint(xyz.x - h[0] - v[0], xyz.y - h[1] - v[1], xyz.z - h[2] - v[2])
                addPoint(xyz.x - h[0] + v[0], xyz.y - h[1] + v[1], xyz.z - h[2] + v[2])
                addPoint(xyz.x + h[0] - v[0], xyz.y + h[1] - v[1], xyz.z + h[2] - v[2])
                addPoint(xyz.x + h[0] + v[0], xyz.y + h[1] + v[1], xyz.z + h[2] + v[2])
             }
            with(coordBuffer){
                addTexCoords(0f, 1f)
                addTexCoords(0f, 0f)
                addTexCoords(1f, 1f)
                addTexCoords(1f, 0f)
            }
        }

        this.images = images
        queueForReload(false)
    }

    override fun reload(gl: GL10, fullReload: Boolean) {
        val images = images

        if (fullReload) {
            textures = arrayOfNulls(images.size)
        }

        for (i in textures.indices) {
            if (textures[i] != null) {
                textures[i]!!.delete(gl)
            }
            val bmp = this.images[i]!!.bitmap
            textures[i] = textureManager().createTexture(gl)
            textures[i]!!.bind(gl)

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0)
        }
    }

    override fun drawInternal(gl: GL10) {
        gl.run {
            if (vertexBuffer.vertexEmpty()) return

            enableGlClients()

            vertexBuffer.set(this)
            coordBuffer.set(this)

            val textures = textures

            for (i in textures.indices) {
                glEnable(GL10.GL_ALPHA_TEST)
                glAlphaFunc(GL10.GL_GREATER, 0.5f)

                textures[i]!!.bind(this)

                (this as GL11).glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4 * i, 4)

                glDisable(GL10.GL_ALPHA_TEST)
            }
            glDisable(GL10.GL_TEXTURE_2D)
        }
    }

    private fun GL10.enableGlClients() {
        glEnable(GL10.GL_TEXTURE_2D)
        glEnableClientState(GL10.GL_VERTEX_ARRAY)
        glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        glDisableClientState(GL10.GL_COLOR_ARRAY)
    }

    private class Image {
        var bitmap: Bitmap? = null
        var useBlending = false
    }
}
