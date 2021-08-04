package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.RaDec
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.LineGrid
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.LineSource
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.SomethResource
import java.util.*


internal class LatLongGrid(res: Resources, numRaSources: Int, numDecSources: Int) :
        SomethResource() {
        private val lineSources = ArrayList<LineSource>()

        private fun createRaLine(index: Int, numRaSources: Int): LineGrid {
            val line = LineGrid()

            val ra = index * 360.0 / numRaSources
            for (i in 0 until 2) {
                val dec = 90.0 - i * 180.0 / 2
                val raDec = RaDec(ra, dec)
                line.raDecs.add(raDec)
                line.vertexGeoc.add(GeocentricCoord.getInstance(raDec))
            }
            val raDec = RaDec(0.0, -90.0)
            line.addRaDec(raDec)
            line.vertexGeoc.add(GeocentricCoord.getInstance(raDec))
            return line
        }

        private fun createDecLine(dec: Double): LineGrid {
            val line = LineGrid()
            for (i in 0 until 36) {
                val ra = i * 360.0 / 36
                val raDec = RaDec(ra, dec)
                line.addRaDec(raDec)
                line.vertexGeoc.add(GeocentricCoord.getInstance(raDec))
            }
            val raDec = RaDec(0.0, dec)
            line.addRaDec(raDec)
            line.vertexGeoc.add(GeocentricCoord.getInstance(raDec))
            return line
        }

        override fun lineResources(): ArrayList<LineSource> {
            return lineSources
        }


        init {
            for (r in 0 until numRaSources) {
                lineSources.add(createRaLine(r, numRaSources))
            }

            lineSources.add(createDecLine(0.0))

            for (d in 1 until numDecSources) {
                val dec = d * 90.0 / numDecSources
                lineSources.add(createDecLine(dec))
                lineSources.add(createDecLine(-dec))

            }
        }
    }
