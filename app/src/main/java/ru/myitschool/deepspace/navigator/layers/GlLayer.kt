package ru.myitschool.deepspace.navigator.layers

import android.content.res.Resources
import ru.myitschool.deepspace.navigator.pointing.VectorPointing
import ru.myitschool.deepspace.navigator.rendertype.Grid
import ru.myitschool.deepspace.navigator.rendertype.ImageRun
import ru.myitschool.deepspace.navigator.rendertype.LineRun
import ru.myitschool.deepspace.navigator.rendertype.Planet
import java.util.*



class GlLayer(resources: Resources?, private val pointing: VectorPointing, val type: TypeLayer) :
    AbstractGlLayer(resources!!) {

    enum class TypeLayer {
        IMAGE, LINE
    }

    private val images = ArrayList<ImageRun>()
    private val lines = ArrayList<LineRun>()

    private val resPlanets = ArrayList<Planet.PlanetSource>()
    private val resGrid = ArrayList<Grid>()

    private var thread: Runnable? = null

    @Synchronized
    override fun redraw() {
        super.redraw(lines, images)
    }

    @Synchronized
    override fun initialize() {
        resPlanets.clear()
        resGrid.clear()

        for (planet in Planet.values()) resPlanets.add(Planet.PlanetSource(planet, resources, pointing))
        resGrid.add(Grid(numRaSources = 24, numDecSources = 9))

        for (planet in resPlanets) {
            val images = planet.setupIcon()
            this.images.addAll(images)
        }
        for(line in resGrid){
            val lines = line.initLine()
            this.lines.addAll(lines)
        }
        runRedrawing()
    }

    override fun runRedrawing() {
        redraw()
        if (type == TypeLayer.IMAGE) {
            if (thread == null)
                thread = Runnable {
                    redraw()
                }
            render(thread)
        }
    }
}
