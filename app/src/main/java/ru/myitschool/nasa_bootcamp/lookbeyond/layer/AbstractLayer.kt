package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererRunner
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.LineSource
import java.util.*
import java.util.concurrent.locks.ReentrantLock


interface Layer {
    fun init(model : AbstractPointing)
}

enum class ClassType {
    IMAGE,
    LINE,
    NO
}

abstract class AbstractLayer(protected val resources: Resources) : Layer {
    private val renderMapLock = ReentrantLock()
    private val type: ClassType = ClassType.NO

    private var renderer: RendererThreadRun? = null

     fun renderIt(threadRun: RendererThreadRun) {
        renderer = threadRun
        updateLayer()
    }

    protected abstract fun updateLayer()


    protected fun repaint(
        lineSources: ArrayList<LineSource>?,
        imageRes: ArrayList<ImageRes>?,
     ) {
        if (renderer == null) return

        renderMapLock.lock()
        try {
            val atomic = renderer!!.createAtomic()

            setSources(lineSources, ClassType.LINE, atomic)
            setSources(imageRes, ClassType.IMAGE, atomic)
            renderer!!.queueRender(atomic)
        } finally {
            renderMapLock.unlock()
        }
    }


    private fun <E> setSources(
        sources: ArrayList<E>?,
        classType: ClassType, runRendering: RendererThreadRun.RunRendering
    ) {
        var manager: RendererRunner.RenderManager? = null
        val type = classType

        when (type) {
            ClassType.IMAGE -> {
                manager = createRenderers(classType, runRendering)
            }
            ClassType.LINE -> {
                manager = createRenderers(classType, runRendering)
            }
            else -> {
            }
        }

        if (sources != null && manager != null) manager.runObjects(sources, runRendering)
    }

    fun createRenderers(
        classType: ClassType,
        controller: RendererRunner
    ): RendererRunner.RenderManager {
        when (classType) {
            ClassType.IMAGE -> {
                    return controller.createImageManager(30)
            }
            ClassType.LINE -> {
                return controller.createLineManager(10)
            }
            else -> {
            }
        }
        throw Exception("No such source") //СДЕЛАТЬ СВОИ Exception на всё
    }

}
