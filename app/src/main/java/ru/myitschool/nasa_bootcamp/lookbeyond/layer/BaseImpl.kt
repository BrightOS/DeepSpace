package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Planet
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.PlanetResources
import ru.myitschool.nasa_bootcamp.lookbeyond.managers.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.InitialResource
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineRes

import java.util.*

class PlanetsBaseLayerImpl(
    private val model: AbstractPointing,
    resources: Resources?
) :
    ResBaseLayerImpl(resources) {

    override fun init(sources: ArrayList<InitialResource>) {
        for (planet in Planet.values()) {
            sources.add(PlanetResources(planet, resources, model))
        }
    }
}

abstract class ResBaseLayerImpl(resources: Resources?) :
    BaseLayerImpl(resources!!) {
    private val imageSources = ArrayList<ImageRes>()
    private val lineSources = ArrayList<LineRes>()
    private val resoursec = ArrayList<InitialResource>()

    override fun init() {
        resoursec.clear()
        init(resoursec)
        for (astroSource in resoursec) {
            val resources = astroSource.create()
            imageSources.addAll(resources.images)
            lineSources.addAll(resources.lines)
        }
        updateLayer()
    }

    override fun updateLayer() {
        super.repaint(lineSources, imageSources)
    }

    protected abstract fun init(sources: ArrayList<InitialResource>)
}


