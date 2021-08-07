package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import android.graphics.Color
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.RaDec
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.InitialResource
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineResImpl
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.SomethResource
import ru.myitschool.nasa_bootcamp.utils.*
import java.util.*


class LatLongGrid(
    resources: Resources?,
    private val raCount: Int,
    private val decCount: Int
) :
    ResBaseLayerImpl(resources) {
    override fun init(sources: ArrayList<InitialResource>) {
        sources.add(GridSource(raCount, decCount))
    }

    internal class GridSource(raCount: Int, decCount: Int) :
        SomethResource() {
       override val lines = ArrayList<LineResImpl>()

        private fun createRaLine(index: Int, raCount: Int): LineResImpl {
            val line = LineResImpl(Color.WHITE)
            val ra = index * DEGREE_360 / raCount
            for (i in 0 until RA_LINES_COUNT) {
                val dec = DEGREE_90 - i * DEGREE_180/ RA_LINES_COUNT
                val raDec = RaDec(ra, dec)
                line.raDecs.add(raDec)
                line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            }
            val raDec = RaDec(0.0, -DEGREE_90)
            line.raDecs.add(raDec)
            line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            return line
        }

        private fun createDecLine(dec: Double): LineResImpl {
            val line = LineResImpl(Color.WHITE)
            for (i in 0 until DEC_LINES_COUNT) {
                val ra = i * DEGREE_360 / DEC_LINES_COUNT
                val raDec = RaDec(ra, dec)
                line.raDecs.add(raDec)
                line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            }
            val raDec = RaDec(0.0, dec)
            line.raDecs.add(raDec)
            line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            return line
        }


        init {
            for (res in 0 until raCount) {
                lines.add(createRaLine(res, raCount))
            }
            lines.add(createDecLine(0.0))
            for (d in 1 until decCount) {
                val dec = d * DEGREE_90 / decCount
                lines.add(createDecLine(dec))
                lines.add(createDecLine(-dec))
            }
        }
    }
}