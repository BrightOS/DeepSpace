package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.*


open class ImagePlanet(
    coords: GeocentricCoord?, protected val resources: Resources?, id: Int, upVec: Vector3D?,
    private val imageScale: Double
) : AbstractSource(coords!!, Color.WHITE),
    ImageRes {
    private var ux = 0.0
    private var uy = 0.0
    private var uz = 0.0
    private var vx = 0.0
    private var vy = 0.0
    private var vz = 0.0
    override lateinit var image: Bitmap


    private fun setImageId(imageId: Int) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        image = BitmapFactory.decodeResource(resources, imageId, options)
    }

    override val horizontalCorner: DoubleArray
        get() = doubleArrayOf(ux, uy, uz)
    override val verticalCorner: DoubleArray
        get() = doubleArrayOf(vx, vy, vz)



    private fun setUpVector(upVec: Vector3D?) {
        val p: Vector3D = location
        val up =  mirrorVector(normalized( crossV(p, upVec!!)))
        val vector =  crossV(up, p)
        vector.scale(imageScale)
        up.scale(imageScale)

        ux = up.x
        uy = up.y
        uz = up.z
        vx = vector.x
        vy = vector.y
        vz = vector.z
    }


    init {
        setUpVector(upVec)
        setImageId(id)
    }
}
