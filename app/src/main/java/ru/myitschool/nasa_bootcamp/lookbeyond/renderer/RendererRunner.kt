package ru.myitschool.nasa_bootcamp.lookbeyond.renderer


abstract class RendererRunner(val renderer: MainRender) {

    abstract class RenderManager internal constructor(var manager: RendererManager) {
        abstract fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        )
    }

    class LineManager internal constructor(texture: LineTexture) :
        RenderManager(texture) {
        override fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        ) {
            controller.runRunnables {
                (manager as LineTexture).updateObjects(
                    (objects as List<LineSource>)
                )
            }
        }
    }

    class ImageManager internal constructor(texture: ImageTexture) :
        RenderManager(texture) {
        override fun <E> runObjects(
            objects: List<E>,
            controller: RendererRunner
        ) {
            controller.runRunnables {
                (manager as ImageTexture).updateObjects(
                    (objects as List<ImageRes>)
                )
            }
        }
    }

    protected interface ObjectRunner {
        fun runObj(r: Runnable?)
    }

    fun createLineManager(layer: Int): RenderManager {
        val manager = LineManager(renderer.createPolyLineManager(layer))
        queueAddManager(manager)
        return manager
    }

    fun createImageManager(layer: Int): RenderManager {
        val manager = ImageManager(renderer.createImageManager(layer))
        queueAddManager(manager)
        return manager
    }

    fun queueFieldOfView(fov: Double) {
        runRunnables { renderer.setRadiusOfView(fov) }
    }

    fun queueTextAngle(angleInRadians: Double) {
        runRunnables { renderer.setTextAngle(angleInRadians) }
    }

    fun setupView(
        lookX: Double, lookY: Double, lookZ: Double,
        upX: Double, upY: Double, upZ: Double
    ) {
        runRunnables { renderer.setViewOrientation(lookX, lookY, lookZ, upX, upY, upZ) }
    }


    fun newRunTask(runnable: Runnable?) {
        runRunnables { renderer.addUpdateClosure(runnable!!) }
    }


    fun queueAddManager(rom: RenderManager) {
        runRunnables { renderer.addObjectManager(rom.manager) }
    }

    protected abstract val queueToRun: ObjectRunner

    protected fun runRunnables(runnable: Runnable) {
        val queuer = queueToRun
        runRunnables(queuer, runnable)
    }

    companion object {
        private fun runRunnables(queuer: ObjectRunner, r: Runnable) {
            queuer.runObj(r)
        }
    }
}