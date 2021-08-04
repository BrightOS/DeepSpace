package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.opengl.GLSurfaceView
import java.util.*

class RendererThreadRun(renderer: MainRender?, view: GLSurfaceView) :

    RendererRunner(renderer!!) {
    class RunRendering(renderer: MainRender) : RendererRunner(renderer) {
        private var queuer = ObjRunnerImpl()

        fun runQueue(): Queue<Runnable?> {
            val queue = queuer.queue
            queuer = ObjRunnerImpl()
            return queue
        }

        private class ObjRunnerImpl : ObjectRunner {
            val queue: Queue<Runnable?> = LinkedList()
            override fun runObj(runnable: Runnable?) {
                queue.add(runnable)
            }
        }

        override val queueToRun: ObjectRunner
            get() = queuer
    }

    override val queueToRun: ObjectRunner


    fun createAtomic(): RunRendering {
        return RunRendering(super.renderer)
    }

    fun queueRender(runRendering: RunRendering) {
        runRunnables() {
            val queue = runRendering.runQueue()
            for (event in queue) {
                event!!.run()
            }
        }
    }

    init {
        queueToRun = object : ObjectRunner {
            override fun runObj(r: Runnable?) {
                view.queueEvent(r)
            }
        }
    }
}