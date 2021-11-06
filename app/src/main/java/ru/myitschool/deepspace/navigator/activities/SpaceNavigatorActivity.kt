package ru.myitschool.deepspace.navigator.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.myitschool.deepspace.databinding.SpaceNavigatorBinding
import ru.myitschool.deepspace.navigator.layers.AbstractGlLayer
import ru.myitschool.deepspace.navigator.layers.GlLayer
import ru.myitschool.deepspace.navigator.managers.GpsManager
import ru.myitschool.deepspace.navigator.managers.Managers
import ru.myitschool.deepspace.navigator.pointing.VectorPointing
import ru.myitschool.deepspace.navigator.renderer.MainRenderer
import ru.myitschool.deepspace.navigator.thread.RendererExecutor
import ru.myitschool.deepspace.navigator.touch.ZoomDetector
import ru.myitschool.deepspace.navigator.touch.Zoomer
import java.util.*

class SpaceNavigatorActivity : Activity() {
    private var _binding: SpaceNavigatorBinding? = null
    private val binding get() = _binding!!

    private val layers: MutableList<AbstractGlLayer> = ArrayList()

    private lateinit var controller: Managers
    private lateinit var gestureDetector: GestureDetector
    private lateinit var pointing: VectorPointing
    private lateinit var rendererExecutor: RendererExecutor
    private lateinit var playServicesChecker: GooglePlayServicesChecker
    private lateinit var zoomDetector: ZoomDetector

    private var handler = Handler(Looper.myLooper()!!)
    private val onResumeRunnables: List<Runnable> = ArrayList()

    private class MainRenderThread(
        private val model: VectorPointing,
        private val rendererExecutor: RendererExecutor,
    ) : Runnable {
        private val horizontalRotation: Boolean = false

        override fun run() {
            val pointing = model.userPointing

            val xyzDirection = pointing.userLook.let { XyzDirection(it.x, it.y, it.z) }
            val upDirection = pointing.perpendicular.let { XyzDirection(it.x, it.y, it.z) }

            rendererExecutor.executeDirections(xyzDirection.x, xyzDirection.y, xyzDirection.z, upDirection.x, upDirection.y, upDirection.z)

            val fieldOfView = model.viewDegree
            rendererExecutor.executeView(fieldOfView)
        }

        private class XyzDirection(val x: Double, val y: Double, val z: Double)

        init {
            model.setHorizontalRotation(horizontalRotation)
        }
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        _binding = SpaceNavigatorBinding.inflate(layoutInflater)
        pointing = VectorPointing()

        initLayers()
        initGps()
        initSystemParams()
        initPermissionsChecker()
        initSurface()
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)
    }


    public override fun onResume() {
        super.onResume()
        binding.spaceNav.onResume()
        controller.start()
        for (runnable in onResumeRunnables) handler.post(runnable)
    }

    public override fun onPause() {
        super.onPause()
        for (runnable in onResumeRunnables) handler.removeCallbacks(runnable)

        controller.stop()
        binding.spaceNav.onPause()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) return true
        if (zoomDetector.onTouchEvent(event)) return true
        return false
    }

    private fun initGps() {
        controller = Managers(
            this,
            GpsManager(this, ContextCompat.getSystemService(this, LocationManager::class.java))
        )
    }

    private fun initSystemParams() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initPermissionsChecker() {
        playServicesChecker = GooglePlayServicesChecker(this)
        playServicesChecker.locServicesEnabled()
    }

    private fun initLayers() {
        layers.apply {
            add(GlLayer(resources, pointing, GlLayer.TypeLayer.IMAGE))
            add(GlLayer(resources, pointing, GlLayer.TypeLayer.LINE))
        }
        for (layer in layers) layer.initialize()
    }

    private fun initSurface() {
        setContentView(binding.root)

        with(binding.spaceNav) {
            setEGLConfigChooser(false)
            val renderer = MainRenderer(resources)
            setRenderer(renderer)
            rendererExecutor = RendererExecutor(renderer, this)
        }

        rendererExecutor.addRender(
            MainRenderThread(
                pointing,
                rendererExecutor
            )
        )

        for (layer in layers) {
            layer.registerWithRenderer(rendererExecutor)
            layer.setVisible()
        }

        controller.pointing = (pointing)
        wireUpScreenControls()

        binding.sensorsButton.setOnClickListener {
            startActivity(Intent(applicationContext, SensorsActivity::class.java))
        }
        binding.info.setOnClickListener {
            startActivity(Intent(applicationContext, PlanetsActivity::class.java))
        }
    }

    private fun wireUpScreenControls() {
        val mapMover = Zoomer(controller, this)
        gestureDetector = GestureDetector(this, GestureDetector.SimpleOnGestureListener())
        zoomDetector = ZoomDetector(mapMover)

    }

    companion object {
        const val LOCATION_PERMISSION_CODE = 2
    }
}

class GooglePlayServicesChecker(private val activity: Activity) {

    fun locServicesEnabled() {
        if (ActivityCompat.checkSelfPermission(activity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestLocPermission()
            }
        }
    }

    private fun requestLocPermission() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            SpaceNavigatorActivity.LOCATION_PERMISSION_CODE
        )
    }
}