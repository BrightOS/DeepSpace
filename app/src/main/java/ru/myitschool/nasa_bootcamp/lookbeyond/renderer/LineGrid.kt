package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Color
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.RaDec
import kotlin.collections.ArrayList


class LineGrid constructor(
    color: Int = Color.WHITE,
    val vertexGeoc: ArrayList<GeocentricCoord> = ArrayList(),
    override val lineWidth: Float = 1.5f
) :
    AbstractSource(color), LineSource {
    val raDecs: ArrayList<RaDec> = ArrayList()

    fun addRaDec(raDec: RaDec) {
        raDecs.add(raDec)
    }


    override fun vertexGeocentric(): ArrayList<GeocentricCoord> {
        val result: ArrayList<GeocentricCoord>
        result = vertexGeoc
        return result
    }

}
