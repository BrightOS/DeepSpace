package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.content.res.Resources
import android.opengl.GLSurfaceView
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Matrix4D
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Vector3D
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.crossProduct
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.collections.HashSet
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

open class MainRender(res: Resources?) : GLSurfaceView.Renderer {
    private val renderState = RenderingValues()
    private var projectionMatrix: Matrix4D? = null
    private var viewMatrix: Matrix4D? = null

    private var updateView = true
    private var updateProjection = true

    private val taasks: MutableSet<Runnable> = HashSet()

    private val objsToReload: ArrayList<RendererManager> = ArrayList()
    private val textureModule: TextureModule

    private class ManagerReloadData(
        var manager: RendererManager,
        var fullReload: Boolean
    )


    private val managersToReload = ArrayList<ManagerReloadData>()

    private var layersToManagersMap: HashMap<Int, MutableSet<RendererManager>>? = null

    override fun onDrawFrame(gl: GL10) {
        for (data in managersToReload) {
            data.manager.reload(gl, data.fullReload)
        }
        managersToReload.clear()
        updateMatrixx(gl)

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

        for (managers in layersToManagersMap!!.values) {
            for (r in managers) {
                r.draw(gl)
            }
        }

        for (update in taasks) {
            update.run()
        }
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        gl.glEnable(GL10.GL_DITHER)

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        gl.glEnable(GL10.GL_CULL_FACE)
        gl.glShadeModel(GL10.GL_SMOOTH)
        gl.glDisable(GL10.GL_DEPTH_TEST)

        textureModule.reload()

        for (rom in objsToReload) {
            rom.reload(gl, true)
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        renderState.setScreenSize(width, height)

        updateView = true
        updateProjection = true
        gl.glViewport(0, 0, width, height)
    }

    fun setRadiusOfView(degrees: Double) {
        renderState.radiusOfView = degrees
        updateProjection = true
    }

    fun addRunTask(update: Runnable) {
        taasks.add(update)
    }

    fun addObjectManager(rendererManager: RendererManager) {
        rendererManager.renderingValues = renderState
        rendererManager.setReloadListener(object : RendererManager.ReloadListener {
            override fun queueForReload(rom: RendererManager, fullReload: Boolean) {
                managersToReload.add(
                    ManagerReloadData(
                        rom,
                        fullReload
                    )
                )
            }
        })
        objsToReload.add(rendererManager)

        managersToReload.add(ManagerReloadData(rendererManager, true))

        var managers = layersToManagersMap!![rendererManager.layer]
        if (managers == null) {
            managers = HashSet()
            layersToManagersMap!![rendererManager.layer] = managers
        }
        managers.add(rendererManager)
    }

    fun removeObjectManager(m: RendererManager) {
        objsToReload.remove(m)
        val managers = layersToManagersMap!![m.layer]!!
        managers.remove(m)
    }


    fun setTextAngle(angleInRadians: Double) {
        val newAngle = Math.round(angleInRadians * (2.0f / Math.PI)) * (Math.PI / 2.0)
        renderState.upAngle = newAngle
    }

    fun setViewOrientation(
        _viewX: Double, _viewY: Double, _viewZ: Double,
        _perpX: Double, _perpY: Double, _perpZ: Double
    ) {
        var perpX = _perpX
        var perpY = _perpY
        var perpZ = _perpZ
        val dirLen = sqrt(_viewX * _viewX + _viewY * _viewY + _viewZ * _viewZ)
        val oneOverDirLen = 1.0f / dirLen

        fun multAll(arr: DoubleArray, koef: Double) {
            for (j in arr.indices)
                arr[j] *= koef
        }

        multAll(doubleArrayOf(_viewX, _viewY, _viewZ), oneOverDirLen)

        //Нужно установить все перпедикулярно вектору взгляда, поэтому вычтем проекцию направления взгляда на вектор вверх
        val lookDotUp = _viewX * perpX + _viewY * perpY + _viewZ * perpZ
        perpX -= lookDotUp * _viewX
        perpY -= lookDotUp * _viewY
        perpZ -= lookDotUp * _viewZ

        //Нормируем
        val upLen = sqrt(perpX * perpX + perpY * perpY + perpZ * perpZ)
        val oneOverUpLen = 1.0 / upLen
        multAll(doubleArrayOf(_viewX, _viewY, _viewZ), oneOverUpLen)

        renderState.lookDir = GeocentricCoord(_viewX, _viewY, _viewZ)
        renderState.upPerpendic = GeocentricCoord(perpX, perpY, perpZ)
        updateView = true

    }

    protected val width: Int
        get() = renderState.screenWidth
    protected val height: Int
        get() = renderState.screenHeight

    private fun updateView(gl: GL10) {
        val lookDir: Vector3D = renderState.lookDir!!
        val upDir: Vector3D = renderState.upPerpendic!!
        val right = crossProduct(lookDir, upDir)
        viewMatrix = Matrix4D.createView(lookDir, upDir, right)
        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadMatrixf(viewMatrix!!.floatArray, 0)
    }

    private fun updatePerspective(gl: GL10) {
        projectionMatrix = Matrix4D.perspectiveProjection(
            renderState.screenWidth.toDouble(),
            renderState.screenHeight.toDouble(),
            renderState.radiusOfView * 3.141593 / 360.0
        )
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadMatrixf(projectionMatrix!!.floatArray, 0)

        gl.glMatrixMode(GL10.GL_MODELVIEW)
    }

    private fun updateMatrixx(gl: GL10) {
        if (updateView) {
            updateView(gl)
            updateView = false
        }
        if (updateProjection) {
            updatePerspective(gl)
            updateProjection = false
        }
    }

    fun createLineTexture(layer: Int): LineTexture {
        return LineTexture(layer, textureModule)
    }

    fun createImageTexture(layer: Int): ImageTexture {
        return ImageTexture(layer, textureModule)
    }

    init {
        layersToManagersMap = HashMap()
        textureModule = TextureModule( )

    }
}

interface RenderingValuesInterface {
    val lookDir: GeocentricCoord?
    val upPerpendic: GeocentricCoord?
    val radiusOfView: Double
    val upAngle: Double
    val cosUpAngle: Double
    val sinUpAngle: Double
    val screenHeight: Int

}

internal class RenderingValues : RenderingValuesInterface {

    override var lookDir: GeocentricCoord?
        get() = lookDirection
        set(dir) {
            lookDirection = dir!!.copy()
        }
    override var upPerpendic: GeocentricCoord?
        get() = upDirection
        set(dir) {
            upDirection = dir!!.copy()
        }
    override var upAngle: Double
        get() = mUpAngle
        set(angle) {
            mUpAngle = angle
            cosUpAngle = cos(angle)
            sinUpAngle = sin(angle)
        }

    fun setScreenSize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

    fun setTransformationMatrix(
        transformToDevice: Matrix4D,
        transformToScreen: Matrix4D
    ) {
        transformToDeviceMatrix = transformToDevice
        transformToScreenMatrix = transformToScreen
    }

    private var lookDirection = GeocentricCoord(1.0, 0.0, 0.0)
    private var upDirection = GeocentricCoord(0.0, 1.0, 0.0)

    override var radiusOfView = 45.0
    private var mUpAngle = 0.0

    override var cosUpAngle = 1.0
        private set

    override var sinUpAngle = 0.0
        private set

    var screenWidth = 100
        private set

    override var screenHeight = 100
        private set

    var transformToDeviceMatrix = Matrix4D.createIdentity()
        private set

    var transformToScreenMatrix = Matrix4D.createIdentity()
        private set


}