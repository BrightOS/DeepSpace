package ru.myitschool.nasa_bootcamp.lookbeyond.resourc

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.*


class LineResImpl constructor(
    color: Int,
    lineWidth: Float = 1.5f
) :
    AbstractRes(color), LineRes {
    val raDecs: ArrayList<RaDec> = ArrayList()
    override val lineWidth: Float

    override val vertexGeocentric: ArrayList<GeocentricCoord> = arrayListOf()
        get() = field


    init {
        this.lineWidth = lineWidth
    }
}

open class ImageResImpl(
    coords: GeocentricCoord?, protected val resources: Resources?, id: Int, upVec: Vector3D?,
    private val imageScale: Double
) : AbstractRes(coords!!, Color.WHITE),
    ImageRes {

    private var ux = 0.0
    private var uy = 0.0
    private var uz = 0.0
    private var vx = 0.0
    private var vy = 0.0
    private var vz = 0.0
    override var image: Bitmap? = null

    private fun setImageId(imageId: Int) {
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        image = BitmapFactory.decodeResource(resources, imageId, opts)
        if (image == null) {
            throw RuntimeException("Coud not decode image $imageId")
        }
    }

    override val horizontalCorner: DoubleArray
        get() = doubleArrayOf(ux, uy, uz)
    override val verticalCorner: DoubleArray
        get() = doubleArrayOf(vx, vy, vz)

    fun setUpVector(upVec: Vector3D?) {
        val p: Vector3D = location
        val u = differ(normalized(crossProduct(p, upVec!!)))
        val v = crossProduct(u, p)
        v.scale(imageScale)
        u.scale(imageScale)

        ux = u.x
        uy = u.y
        uz = u.z
        vx = v.x
        vy = v.y
        vz = v.z
    }


    init {

        setUpVector(upVec)
        setImageId(id)
    }
}