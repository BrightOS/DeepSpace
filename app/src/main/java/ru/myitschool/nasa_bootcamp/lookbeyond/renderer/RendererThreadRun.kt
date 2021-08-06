package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.opengl.GLSurfaceView
import java.util.*


class RendererThreadRun(renderer: MainRender?, view: GLSurfaceView) :
    RendererRunner(renderer!!) {

    class RunRendering internal constructor(renderer: MainRender) :
        RendererRunner(renderer) {
        private var queuer = Queuer()

        override val queueToRun: ObjectRunner
            get() = queuer


        internal fun runQueue(): Queue<Runnable> {
            val queue = queuer.queue
            queuer = Queuer()
            return queue
        }

        private class Queuer : ObjectRunner {
            val queue: Queue<Runnable> = LinkedList()

            override fun runObj(r: Runnable) {
                queue.add(r)
            }
        }
    }

    override val queueToRun: ObjectRunner
        get() = field


    fun createQueue(): RunRendering {
        return RunRendering(renderer)
    }

    fun queueRender(runRendering: RunRendering) {
        runRunnables {
            val events = runRendering.runQueue()
            for (event in events) {
                event.run()
            }
        }
    }

    init {
        queueToRun = object : ObjectRunner {
            override fun runObj(r: Runnable) {
                view.queueEvent(r)
            }
        }
    }
}