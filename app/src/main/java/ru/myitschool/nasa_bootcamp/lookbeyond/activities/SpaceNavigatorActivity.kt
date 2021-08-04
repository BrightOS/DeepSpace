package ru.myitschool.nasa_bootcamp.lookbeyond.activities

import android.app.Activity
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import ru.myitschool.nasa_bootcamp.App
import ru.myitschool.nasa_bootcamp.databinding.SpaceNavigatorBinding
import ru.myitschool.nasa_bootcamp.lookbeyond.layer.AbstractResLayer
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.Controllers
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.SensorOrientationStartStop
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.VectorPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.MainRender
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import kotlin.math.atan2


class SpaceNavigatorActivity : Activity() {
    private var _binding: SpaceNavigatorBinding? = null
    private val binding get() = _binding!!

    private class RenderThread(
        private val p: AbstractPointing?,
        private val rendererThreadRun: RendererThreadRun,
    ) : Runnable {
        override fun run() {

            val directionX = p!!.pointing.lineOfSightX
            val directionY = p.pointing.lineOfSightY
            val directionZ = p.pointing.lineOfSightZ
            val upX = p.pointing.perpendicularX
            val upY = p.pointing.perpendicularY
            val upZ = p.pointing.perpendicularZ

            rendererThreadRun.setupView(
                directionX,
                directionY,
                directionZ,
                upX,
                upY,
                upZ
            )
            val up = this.p.phoneUpDirection
            rendererThreadRun.queueTextAngle(atan2(up.x, up.y))
            val fieldOfView = this.p.fieldOfView
            rendererThreadRun.queueFieldOfView(fieldOfView)
        }
    }


    private lateinit var controller: Controllers
    lateinit var model: AbstractPointing


    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        _binding = SpaceNavigatorBinding.inflate(layoutInflater)

        model = VectorPointing()
        controller = Controllers(
            SensorOrientationStartStop(
                model,
                ContextCompat.getSystemService(application as App, SensorManager::class.java)!!
            )
        )


        initializeModelViewController()
        binding.sensorsButton.setOnClickListener {
            startActivity(Intent(applicationContext, SensorsActivity::class.java))
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return true
    }

    public override fun onResume() {
        super.onResume()
        binding.spaceNav.onResume()
        controller.start()
    }

    public override fun onPause() {
        super.onPause()
        controller.stop()
        binding.spaceNav.onPause()
    }

    private fun initializeModelViewController() {
        setContentView(binding.root)

        binding.spaceNav.setEGLConfigChooser(false)
        val renderer = MainRender(resources)
        binding.spaceNav.setRenderer(renderer)

        val rendererController = RendererThreadRun(renderer, binding.spaceNav)

        rendererController.newRunTask(
            RenderThread(model, rendererController)
        )

        val layer = AbstractResLayer(resources)
        layer.init(model)
        layer.renderIt(rendererController)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {}
}