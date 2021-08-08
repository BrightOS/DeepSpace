package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources

import ru.myitschool.nasa_bootcamp.lookbeyond.managers.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import java.util.*


class LayerManager(resources : Resources, model: AbstractPointing){
    private val baseLayers: MutableList<BaseLayer> = ArrayList()
     fun addLayer(baseLayer: BaseLayer) {
        baseLayers.add(baseLayer)

    }

    init {
        baseLayers.add(PlanetsBaseLayerImpl(model, resources))
        baseLayers.add(LatLongGrid(resources, 24, 9))
         for (layer in baseLayers) {
            layer.init()
        }
    }

    fun renderLayouts(renderer: RendererThreadRun?) {
        for (layer in baseLayers) {
            layer.renderIt(renderer!!)
        }
    }
}