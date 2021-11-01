package ru.myitschool.nasa_bootcamp.lookbeyond.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.location.LocationManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.databinding.SpaceNavigatorBinding
import ru.myitschool.nasa_bootcamp.lookbeyond.layer.LayerManager
import ru.myitschool.nasa_bootcamp.lookbeyond.managers.*
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.MainRender
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RenderViewModel
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import kotlin.math.atan2


class SpaceNavigatorActivity : AppCompatActivity() {
    private var _binding: SpaceNavigatorBinding? = null
    private val binding get() = _binding!!
    val viewModel: RenderViewModel by viewModels()

    private class RenderThread(
        private val p: AbstractPointing?,
        private val rendererThreadRun: RendererThreadRun,
    ) : Runnable {
        override fun run() {

            val directionX = p!!.pointing!!.getLookDirection().x
            val directionY = p.pointing!!.getLookDirection().y
            val directionZ = p.pointing!!.getLookDirection().z
            val upX = p.pointing!!.getPerpendicular().x
            val upY = p.pointing!!.getPerpendicular().y
            val upZ = p.pointing!!.getPerpendicular().z

            rendererThreadRun.setViewOrientation(
                directionX,
                directionY,
                directionZ,
                upX,
                upY,
                upZ
            )
            val up = p.phoneUpDirVector
            rendererThreadRun.queueTextAngle(atan2(up.x, up.y))
            val fieldOfView = p.viewOfUser
            rendererThreadRun.queueFieldOfView(fieldOfView)
        }
    }


    private lateinit var controller: Managers
    lateinit var model: AbstractPointing

    private var rendererThreadRun: RendererThreadRun? = null
    private var skyView: GLSurfaceView? = null
    private var layerManager: LayerManager? = null
    private var playServicesChecker: GooglePlayServicesChecker? = null


    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        _binding = SpaceNavigatorBinding.inflate(layoutInflater)

        controller = Managers(
            SensorManager(
                ContextCompat.getSystemService(
                    this,
                    SensorManager::class.java
                )!!
            ),
            GpsManager(
                applicationContext, ContextCompat.getSystemService(
                    this,
                    LocationManager::class.java
                )
            )
        )
        playServicesChecker = GooglePlayServicesChecker(
            this
        )

        model = VectorPointing()

        layerManager = LayerManager(resources, model)

        playServicesChecker!!.locServicesEnabled()


        setContentView(binding.root)

        skyView = binding.spaceNav
        skyView!!.setEGLConfigChooser(false)
        val renderer = MainRender()
        skyView!!.setRenderer(renderer)

        rendererThreadRun = RendererThreadRun(renderer, skyView!!)
        rendererThreadRun!!.newRunTask(
            RenderThread(
                model,
                rendererThreadRun!!
            ),
            viewModel
        )



        layerManager!!.renderLayouts(rendererThreadRun)
        controller.model = model

        binding.spaceNav.visibility = View.VISIBLE

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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val LOCATION_PERMISSION_CODE = 2
    }
}

class GooglePlayServicesChecker(val parentA: Activity?) {

    fun locServicesEnabled() {
        if (ActivityCompat.checkSelfPermission(
                parentA!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    parentA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                requestLocPermission()
            }
        }
    }

    private fun requestLocPermission() {
        ActivityCompat.requestPermissions(
            parentA!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            SpaceNavigatorActivity.LOCATION_PERMISSION_CODE
        )
    }


}