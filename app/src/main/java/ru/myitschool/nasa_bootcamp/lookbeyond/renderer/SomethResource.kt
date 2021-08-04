package ru.myitschool.nasa_bootcamp.lookbeyond.renderer


import kotlin.collections.ArrayList


abstract class SomethResource : InitialResource, PlanetaryResources {
    override fun initialize(): PlanetaryResources {
        return this
    }


    override fun imagesResources(): ArrayList<ImageRes> {
        return arrayListOf()
    }


    override fun lineResources(): ArrayList<LineSource> {
        return arrayListOf()
    }

}
