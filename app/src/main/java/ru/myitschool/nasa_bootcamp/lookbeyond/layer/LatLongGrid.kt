package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import android.graphics.Color
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.RaDec
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.InitialResource
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineResImpl
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.SomethResource
import java.util.*


class LatLongGrid(
    resources: Resources?,
    private val raCount: Int,
    private val decCount: Int
) :
    AbstractResLayer(resources) {
    override fun init(sources: ArrayList<InitialResource>) {
        sources.add(GridSource(raCount, decCount))
    }

    internal class GridSource(numRaSources: Int, numDecSources: Int) :
        SomethResource() {
       override val lines = ArrayList<LineResImpl>()

        private fun createRaLine(index: Int, numRaSources: Int): LineResImpl {
            val line = LineResImpl(Color.WHITE)
            val ra = index * 360.0 / numRaSources
            for (i in 0 until 2) {
                val dec = 90.0 - i * 180.0 / 2
                val raDec = RaDec(ra, dec)
                line.raDecs.add(raDec)
                line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            }
            val raDec = RaDec(0.0, -90.0)
            line.raDecs.add(raDec)
            line.vertexGeocentric.add(GeocentricCoord.getInstance(raDec))
            return line
        }

        private fun createDecLine(dec: Double): LineResImpl {
            val line = LineResImpl(Color.WHITE)
            for (i in 0 until 36) {
                val ra = i * 360.0 / 36
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
            for (r in 0 until numRaSources) {
                lines.add(createRaLine(r, numRaSources))
            }
            lines.add(createDecLine(0.0))
            for (d in 1 until numDecSources) {
                val dec = d * 90.0 / numDecSources
                lines.add(createDecLine(dec))
                lines.add(createDecLine(-dec))
            }
        }
    }
}