package ru.myitschool.deepspace.navigator.renderer

import android.content.res.Resources
import android.opengl.GLSurfaceView
import ru.myitschool.deepspace.maths.coords.GeocentricCoordinates
import ru.myitschool.deepspace.maths.matrix.Vector3D
import ru.myitschool.deepspace.maths.matrix.mult
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension.Companion.createPerspectiveProjection
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension.Companion.createScaling
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension.Companion.createTranslation
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension.Companion.createView

import ru.myitschool.deepspace.navigator.renderer.Renderer.OnUpdateListener
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.sqrt

open class MainRenderer(res: Resources?) : GLSurfaceView.Renderer {
    private val state = UserViewControl()

    private var projectionMatrix4Dimension: Matrix4Dimension? = null
    private var viewMatrix4Dimension: Matrix4Dimension? = null
    private var viewChanged = true
    private var projChanged = true

    private val updateClosures: MutableSet<Runnable> = HashSet()

    private val onUpdateListener: OnUpdateListener = object : OnUpdateListener {
        override fun runRefresh(rom: Renderer, fullReload: Boolean) {
            managersToReload.add(ManagerReloadData(rom, fullReload))
        }
    }

    private val allManagers: MutableSet<Renderer> = HashSet()
    private val textureManager: TextureManager

    private class ManagerReloadData(var manager: Renderer, var fullReload: Boolean)

    private val managersToReload = ArrayList<ManagerReloadData>()
    private var renderers: HashMap<Int, MutableSet<Renderer>>

    override fun onDrawFrame(gl: GL10) {
        for (data in managersToReload)
            data.manager.reload(gl, data.fullReload)

        managersToReload.clear()
        gl.updateViewMatrix()
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

        for (managers in renderers.values)
            for (rom in managers) rom.drawInternal(gl)

        for (update in updateClosures) update.run()
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        gl.run {
            glEnable(GL10.GL_DITHER)
            glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
            glClearColor(0.0f, 0.2f, 0.5f, 0.0f) //!
            glShadeModel(GL10.GL_SMOOTH)
            glDisable(GL10.GL_DEPTH_TEST)
            textureManager.reset()
            for (rom in allManagers) rom.reload(this, true)
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        state.setScreenSize(width, height)
        viewChanged = true
        projChanged = true
        gl.glViewport(0, 0, width, height)
    }

    fun setRadiusOfView(degrees: Double) {
        state.radiusOfView = degrees
        projChanged = true
    }

    fun addUpdateClosure(update: Runnable) {
        updateClosures.add(update)
    }

    fun removeUpdateCallback(update: Runnable) {
        updateClosures.remove(update)
    }

    fun addObjectManager(renderManager: Renderer) {
        renderManager.refresher = onUpdateListener
        allManagers.add(renderManager)

        managersToReload.add(ManagerReloadData(renderManager, true))

        var managers = renderers[renderManager.layer]
        if (managers == null) {
            managers = HashSet()
            renderers[renderManager.layer] = managers
        }
        managers.add(renderManager)
    }

    inner class ViewOrientation(
        private var dirX: Double,
        private var dirY: Double,
        private var dirZ: Double,
        private var upX: Double,
        private var upY: Double,
        private var upZ: Double,
    ) {

        private fun changeCoords(func: (Double) -> Double) {
            dirX = func(dirX)
            dirY = func(dirY)
            dirZ = func(dirZ)
        }

        private fun mult(value: Double): Double {
            val len = sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ)
            return value * (1.0f / len)
        }

        // нормируем вектор
        private fun multUp(value: Double): Double {
            val upLen = sqrt(upX * upX + upY * upY + upZ * upZ)
            return value * (1.0f / upLen)
        }

        //перпендикуляр к направлению взгляда пользователя, вычитаем проекцию направления взгляда на вектор вверх
        private fun minus(value: Double): Double {
            val lookUp = dirX * upX + dirY * upY + dirZ * upZ
            return value - lookUp * dirX
        }

        fun execute() {
            changeCoords(::mult)
            changeCoords(::minus)
            changeCoords(::multUp)
            state.apply {
                viewDirection = GeocentricCoordinates(dirX, dirY, dirZ)
                upDirection = GeocentricCoordinates(upX, upY, upZ)
            }
            viewChanged = true
        }
    }

    var viewOrientation: ViewOrientation = ViewOrientation(
        dirX = 0.0,
        dirY = 0.0,
        dirZ = 0.0,
        upX = 0.0,
        upY = 0.0,
        upZ = 0.0
    )


    private fun GL10.updateView() {
        //Получить перпендикуляр, направленный вправо, умножив на вектор направления пользователя
        val lookDir: Vector3D = state.viewDirection
        val upDir: Vector3D = state.upDirection
        val right = lookDir.mult(upDir)
        viewMatrix4Dimension = createView(lookDir, upDir, right)
        this.glMatrixMode(GL10.GL_MODELVIEW)
        this.glLoadMatrixf(viewMatrix4Dimension!!.floatArray, 0)
    }

    private fun GL10.updatePerspective() {
        projectionMatrix4Dimension = createPerspectiveProjection(
            state.screenWidth.toDouble(),
            state.screenHeight.toDouble(),
            state.radiusOfView * 3.141593f / 360.0f
        )
        setupGlModes()
    }

    private fun GL10.setupGlModes() {
        this.glMatrixMode(GL10.GL_PROJECTION)
        this.glLoadMatrixf(projectionMatrix4Dimension!!.floatArray, 0)
        this.glMatrixMode(GL10.GL_MODELVIEW)
    }

    private fun GL10.updateViewMatrix() {
        if (viewChanged) {
            this.updateView()
            viewChanged = false
        }
        if (projChanged) {
            this.updatePerspective()
            projChanged = false
        }
        if (viewChanged || projChanged) {
            val transformToDevice = projectionMatrix4Dimension!! * viewMatrix4Dimension!!
            val translate = createTranslation(1.0, 1.0, 0.0)
            val scale = createScaling(
                state.screenWidth * 0.5,
                state.screenHeight * 0.5, 1.0
            )
            val transformToScreen = scale * translate * transformToDevice
        }
    }

    fun createPolyLineManager(layer: Int): LineManager {
        return LineManager(layer, textureManager)
    }

    fun createImageManager(layer: Int): ImageManager {
        return ImageManager(
            layer = layer,
            manager = textureManager
        )
    }

    init {
        state.resources = res
        renderers = HashMap()
        textureManager = TextureManager(res!!)
    }
}

internal interface DeviceStates {
    val radiusOfView: Double
    val resources: Resources?
    val nightVisionMode: Boolean
}

internal class UserViewControl : DeviceStates {
    var viewDirection = GeocentricCoordinates(1.0, 0.0, 0.0)
    var upDirection = GeocentricCoordinates(0.0, 1.0, 0.0)

    override var radiusOfView = 45.0

    var screenWidth = 100
    var screenHeight = 100

    override var resources: Resources? = null
    override var nightVisionMode = false


    fun setScreenSize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

}