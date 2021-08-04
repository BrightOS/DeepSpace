package ru.myitschool.nasa_bootcamp.lookbeyond.maths

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.SomethResource
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.ImagePlanet
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.PlanetaryResources

import java.util.*
import kotlin.collections.ArrayList


class PlanetSource(
    private val planet: Planet, private val resources: Resources,
    private val model: AbstractPointing
) : SomethResource() {

    private val planetImages = ArrayList<ImagePlanet>()
    private val currentCoords = GeocentricCoord(0.0, 0.0, 0.0)
    private var sunCoords: HeliocentricCoords? = null
    private var imageId = -1
    private var lastUpdateTimeMs = 0L


    private fun updateCoords(time: Date) {
        lastUpdateTimeMs = time.time
        sunCoords = HeliocentricCoords.getInstance(Planet.Sun, time)
        currentCoords.updateFromRaDec(
            RaDec.getInstance(planet, time, sunCoords!!).ra,
            RaDec.getInstance(planet, time, sunCoords!!).dec
        )

    }

    override fun initialize(): PlanetaryResources {
        val time = model.time
        updateCoords(time)

        imageId = planet.imageResourceId

        if (planet == Planet.Moon) {
            planetImages.add(
                ImagePlanet(
                    currentCoords, resources, imageId, sunCoords,
                    planet.planetsImageScale
                )
            )
        } else {
            planetImages.add(
                ImagePlanet(
                    currentCoords, resources, imageId, Vector3D(0.0, 1.0, 0.0),
                    planet.planetsImageScale
                )
            )

        }
        return this
    }


    override fun imagesResources(): ArrayList<ImageRes> {
        return planetImages as ArrayList<ImageRes>
    }

}
