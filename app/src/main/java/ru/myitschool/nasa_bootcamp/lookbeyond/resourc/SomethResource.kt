package ru.myitschool.nasa_bootcamp.lookbeyond.resourc


abstract class SomethResource : InitialResource, PlanetaryResources {
    override fun create(): PlanetaryResources {
        return this
    }
    override val images: List<ImageRes>
        get() = emptyList()


    override val lines: List<LineRes>
        get() = emptyList()

}
