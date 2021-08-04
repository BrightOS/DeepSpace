package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.content.res.Resources
import android.opengl.GLSurfaceView
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord

import ru.myitschool.nasa_bootcamp.lookbeyond.maths.Matrix4x4
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.crossV
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.sqrt


open class MainRender(res: Resources) : GLSurfaceView.Renderer {
    private val renderState = RenderState()
    private lateinit var projectionMatrix: Matrix4x4
    private lateinit var viewMatrix: Matrix4x4

    //Изменилась ли матрица с момента прошлого обновления
    private var viewUpdate = true
    private var projUpdate = true

    private val runnables: MutableSet<Runnable> = HashSet()


    //Все что надо перегрузить при обновлении холста
    private val objsToReload: MutableList<RendererManager> = ArrayList()

    protected val textureModule: TextureModule

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
        maybeUpdateMatrices(gl)

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
        for (managers in layersToManagersMap!!.values) {
            for (rom in managers) {
                rom.render(gl)
            }
        }
        for (update in runnables) {
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
        viewUpdate = true
        projUpdate = true

        gl.glViewport(0, 0, width, height)
    }

    fun setRadiusOfView(degrees: Double) {
        renderState.radiusOfView = degrees
        projUpdate = true
    }

    fun addUpdateClosure(update: Runnable) {
        runnables.add(update)
    }

    fun addObjectManager(rendererManager: RendererManager) {
        rendererManager.renderState = renderState
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
        _perpX: Double, _upY: Double, _perpZ: Double
    ) {
        var viewX = _viewX
        var viewY = _viewY
        var viewZ = _viewZ
        var perpX = _perpX
        var perpY = _upY
        var perpZ = _perpZ
        val dirLen = sqrt(viewX * viewX + viewY * viewY + viewZ * viewZ)
        val oneOverDirLen = 1.0f / dirLen

        fun multAll(arr : DoubleArray, koef : Double){
            for (j in arr.indices)
                arr[j]*=koef
        }

        multAll(doubleArrayOf(viewX, viewY, viewZ), oneOverDirLen)

        //Нужно установить все перпедикулярно вектору взгляда, поэтому вычтем проекцию направления взгляда на вектор вверх
        val lookDotUp = viewX * perpX + viewY * perpY + viewZ * perpZ
        perpX -= lookDotUp * viewX
        perpY -= lookDotUp * viewY
        perpZ -= lookDotUp * viewZ

        //Нормируем
        val upLen = sqrt(perpX * perpX + perpY * perpY + perpZ * perpZ)
        val oneOverUpLen = 1.0 / upLen
        multAll(doubleArrayOf(viewX, viewY, viewZ), oneOverUpLen)

        renderState.lookDir = GeocentricCoord(viewX, viewY, viewZ)
        renderState.upDir = GeocentricCoord(perpX, perpY, perpZ)
        viewUpdate = true

    }

    protected val width: Int
        get() = renderState.screenWidth

    protected val height: Int
        get() = renderState.screenHeight

    private fun updateView(gl: GL10) {
        //Получаем вектор, перпендик обоим, направленный вправо, пересека lookDir
        val right = crossV(renderState.lookDir!!, renderState.upDir!!)
        viewMatrix = Matrix4x4.createMatrixViaLookDirection(renderState.lookDir!!, renderState.upDir!!, right)

        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadMatrixf(viewMatrix.floatArray, 0)
    }

    private fun updatePerspective(gl: GL10) {
        projectionMatrix = Matrix4x4.perspectiveProjection(
            renderState.screenWidth.toDouble(),
            renderState.screenHeight.toDouble(),
            (renderState.radiusOfView * Math.PI / 360.0)
        )
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadMatrixf(projectionMatrix.floatArray, 0)

        gl.glMatrixMode(GL10.GL_MODELVIEW)
    }

    private fun maybeUpdateMatrices(gl: GL10) {
        if (viewUpdate) {
            updateView(gl)
            viewUpdate = false
        }
        if (projUpdate) {
            updatePerspective(gl)
            projUpdate = false
        }
    }


    fun createPolyLineManager(layer: Int): LineTexture {
        return LineTexture(layer, textureModule)
    }


    fun createImageManager(layer: Int): ImageTexture {
        return ImageTexture(layer, textureModule)
    }


    init {
        layersToManagersMap = HashMap()
        textureModule = TextureModule()
    }
}

interface RenderStateInterface {
    val lookDir: GeocentricCoord?
    val upDir: GeocentricCoord?
    val radiusOfView: Double
    val upAngle: Double
    val cosUpAngle: Double
    val screenWidth: Int
    val screenHeight: Int
}

internal class RenderState : RenderStateInterface {

    override var lookDir: GeocentricCoord?
        get() = lookDirection
        set(dir) {
            lookDirection = dir!!.copy()
        }
    override var upDir: GeocentricCoord?
        get() = upDirection
        set(dir) {
            upDirection = dir!!.copy()
        }
    override var upAngle: Double
        get() = mUpAngle
        set(angle) {
            mUpAngle = angle
            cosUpAngle = Math.cos(angle)
        }

    fun setScreenSize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

    private var lookDirection = GeocentricCoord(1.0, 0.0, 0.0)
    private var upDirection = GeocentricCoord(0.0, 1.0, 0.0)

    override var radiusOfView = 45.0

    private var mUpAngle = 0.0

    override var cosUpAngle = 1.0
        private set

    override var screenWidth = 500
        private set

    override var screenHeight = 500
        private set

}