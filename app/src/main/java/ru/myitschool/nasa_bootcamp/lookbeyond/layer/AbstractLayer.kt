package ru.myitschool.nasa_bootcamp.lookbeyond.layer

import android.content.res.Resources
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererRunner
import ru.myitschool.nasa_bootcamp.lookbeyond.renderer.RendererThreadRun
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineRes

import java.util.*
import java.util.concurrent.locks.ReentrantLock

interface Layer {
    fun init()
    fun renderIt(threadRun: RendererThreadRun)
}


enum class ClassType {
    IMAGE,
    LINE,
    NO
}

abstract class AbstractLayer(protected val resources: Resources) : Layer {
    private val renderMapLock = ReentrantLock()
    private val renderMap = HashMap<ClassType, RendererRunner.RenderManager>()
    private var renderer: RendererThreadRun? = null


    protected abstract fun updateLayer()

    protected fun addNewRunTask(closure: Runnable?) {
        if (renderer != null) {
            renderer!!.newRunTask(closure)
        }
    }

    override fun renderIt(threadRun: RendererThreadRun) {
        renderMap.clear()
        renderer = threadRun
        updateLayer()
    }

    protected fun repaint(
        lineRes: ArrayList<LineRes>,
        imageRes: ArrayList<ImageRes>,
    ) {

        if (renderer == null) {
            return
        }
        renderMapLock.lock()
        try {
            val atomic = renderer!!.createQueue()
            setSources(lineRes, ClassType.LINE, atomic)
            setSources(imageRes, ClassType.IMAGE, atomic)
            renderer!!.queueRender(atomic)
        } finally {
            renderMapLock.unlock()
        }
    }

    private fun <E> setSources(
        sources: ArrayList<E>?,
        classType: ClassType, run: RendererThreadRun.RunRendering
    ) {
        var manager = renderMap[classType] as RendererRunner.RenderManager?
        if (sources == null || sources.isEmpty()) {
            if (manager != null) {
                manager.runObjects<E>(emptyList(), run)
            }
            return
        }
        if (manager == null) {
            manager = createRenderManager(classType, run)
            renderMap[classType] = manager
        }
        manager.runObjects(sources, run)
    }

    fun createRenderManager(
        classType: ClassType,
        controller: RendererRunner
    ): RendererRunner.RenderManager {

        when (classType) {
            ClassType.IMAGE -> return controller.createImageManager(30)
            ClassType.LINE -> return controller.createLineManager(10)
            else -> {
            }
        }
        throw Exception("No such source") //СДЕЛАТЬ СВОИ Exception на всё
    }

}