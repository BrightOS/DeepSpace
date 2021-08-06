package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources

import ru.myitschool.nasa_bootcamp.lookbeyond.control.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import java.util.*


class LayerManager(resources : Resources, model: AbstractPointing){
    private val layers: MutableList<Layer> = ArrayList()
     fun addLayer(layer: Layer) {
        layers.add(layer)
    }

    init {
        layers.add(PlanetsLayer(model, resources))
        layers.add(LatLongGrid(resources, 24, 9))
         for (layer in layers) {
            layer.init()
        }
    }

    fun renderLayouts(renderer: RendererThreadRun?) {
        for (layer in layers) {
            layer.renderIt(renderer!!)
        }
    }
}