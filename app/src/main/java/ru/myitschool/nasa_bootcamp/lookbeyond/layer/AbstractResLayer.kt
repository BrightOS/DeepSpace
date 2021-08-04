package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.Planet
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.PlanetSource
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.InitialResource
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.LineSource
import java.util.*

class AbstractResLayer(resources: Resources?) :
    AbstractLayer(resources!!) {
    private val imageSources = ArrayList<ImageRes>()
    private val lineSources = ArrayList<LineSource>()
    private val sources = ArrayList<InitialResource>()

    @Synchronized
    override fun init(model: AbstractPointing) {
        sources.clear()

        for (planet in Planet.values()) {
            sources.add(PlanetSource(planet, resources, model))
        }
        sources.add(LatLongGrid(resources, 24, 10))

        for (astroSource in sources) {
            val sources = astroSource.initialize()
            imageSources.addAll(sources.imagesResources())
            lineSources.addAll(sources.lineResources())
        }
    }

    override fun updateLayer() {
        super.repaint(lineSources, imageSources)
    }




}
