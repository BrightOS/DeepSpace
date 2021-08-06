package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Planet
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.PlanetResources
import ru.myitschool.nasa_bootcamp.lookbeyond.managers.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.InitialResource
import java.util.*


class PlanetsLayer(
    private val model: AbstractPointing,
    resources: Resources?
) :
    AbstractResLayer(resources) {

    override fun init(sources: ArrayList<InitialResource>) {
        for (planet in Planet.values()) {
            sources.add(PlanetResources(planet, resources, model))
        }
    }
}