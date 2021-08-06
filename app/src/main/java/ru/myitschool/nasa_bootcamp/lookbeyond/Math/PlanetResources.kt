package ru.myitschool.nasa_bootcamp.lookbeyond.Math

import android.content.res.Resources

import ru.myitschool.nasa_bootcamp.lookbeyond.control.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageResImpl
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.PlanetaryResources
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.SomethResource
import java.util.*

class PlanetResources(
    private val planet: Planet, private val resources: Resources,
    private val model: AbstractPointing
) : SomethResource() {

    private val imageSources = ArrayList<ImageResImpl>()
    private val currentCoords = GeocentricCoord(0.0, 0.0, 0.0)
    private var sunCoords: HeliocentricCoords? = null
    private var imageId = -1
    private var lastUpdateTimeMs = 0L


    private fun updateCoords(time: Date) {
        lastUpdateTimeMs = time.time
        sunCoords = HeliocentricCoords.getInstance(Planet.Sun, time)
        currentCoords.updateFromRaDec(RaDec.getInstance(planet, time, sunCoords!!))
        for (imageSource in imageSources) {
            imageSource.setUpVector(sunCoords)
        }
    }

    override fun create(): PlanetaryResources {
        val time = model.time
        updateCoords(time!!)

        imageId = planet.getImageResourceId()
        if (planet == Planet.Moon) {
            imageSources.add(
                ImageResImpl(
                    currentCoords, resources, imageId, sunCoords,
                    planet.planetaryImageSize
                )
            )
        } else {

            imageSources.add(
                ImageResImpl(
                    currentCoords, resources, imageId, Vector3D(0.0, 1.0, 0.0),
                    planet.planetaryImageSize
                )
            )

        }
        return this
    }

    override val images: List<ImageRes>
        get() = imageSources

}