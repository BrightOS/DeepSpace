package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.ImageRes
import ru.myitschool.nasa_bootcamp.lookbeyond.resourc.LineRes


abstract class RendererRunner(protected val renderer: MainRender) {
    abstract class RenderManager  internal constructor
        (var manager: RendererManager) {

        abstract fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        )
    }

    class LineManager internal constructor(manager: LineTexture) :
        RenderManager (manager) {
        override fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        ) {
            controller.runRunnables() {
                (manager as LineTexture).updateObjects(
                    objects as List<LineRes>
                )
            }
        }
    }

    class ImageManager internal constructor(manager: ImageTexture) :
        RenderManager (manager) {
        override fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        ) {
            controller.runRunnables() {
                (manager as  ImageTexture)
                    .updateObjects(objects as List<ImageRes>)
            }
        }
    }

    interface ObjectRunner {
        fun runObj(r: Runnable)
    }

    fun createLineManager(layer: Int): LineManager {
        val manager = LineManager(renderer.createLineTexture(layer))
        queueAddManager (manager)
        return manager
    }

    fun createImageManager(layer: Int): ImageManager {
        val manager = ImageManager(renderer.createImageTexture(layer))
        queueAddManager(manager)
        return manager
    }

    fun queueFieldOfView(fov: Double) {
        runRunnables { renderer.setRadiusOfView(fov) }
    }

    fun queueTextAngle(angleInRadians: Double) {
        runRunnables { renderer.setTextAngle(angleInRadians) }
    }

    fun setViewOrientation(
        lookX: Double, lookY: Double, lookZ: Double,
        upX: Double, upY: Double, upZ: Double
    ) {
        runRunnables { renderer.setViewOrientation(lookX, lookY, lookZ, upX, upY, upZ) }
    }

    fun newRunTask(runnable: Runnable?) {
        runRunnables { renderer.addRunTask(runnable!!) }
    }

    fun  queueAddManager(rom: RenderManager ) {
        runRunnables { renderer.addObjectManager(rom.manager) }
    }

    protected abstract val queueToRun: ObjectRunner

    protected fun runRunnables(r: Runnable) {
        val queuer = queueToRun
        runRunnables(queuer, r)
    }

    companion object {
        protected fun runRunnables(queuer: ObjectRunner, r: Runnable) {
            queuer.runObj(r)
        }


    }
}