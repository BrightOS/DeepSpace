package ru.myitschool.deepspace.navigator.thread

import android.opengl.GLSurfaceView
import ru.myitschool.deepspace.navigator.renderer.MainRenderer
import java.util.*

class RendererExecutor(renderer: MainRenderer, surface: GLSurfaceView) : RendererTypeExecutorHelper(renderer) {

    class RenderThread internal constructor(renderer: MainRenderer) : RendererTypeExecutorHelper(renderer) {
        private var eventsRunner = EventRunner()
        override val _runner: Runner
            get() = eventsRunner

     internal fun releaseEvents(): Queue<Runnable> {
            val queue = eventsRunner.queue
            eventsRunner = EventRunner()
            return queue
        }

        private class EventRunner : Runner {
            val queue: Queue<Runnable> = LinkedList()
            override fun run(r: Runnable) {
                queue.add(r)
            }
        }
    }

    override val _runner: Runner

    fun createThread(): RenderThread {
        return RenderThread(renderer)
    }

    infix fun run(atomic: RenderThread) {
        execute {
            val events = atomic.releaseEvents()
            for (r in events) r.run()
        }
    }

    init {
        _runner = object : Runner {
            override fun run(r: Runnable) {
                surface.queueEvent(r)
            }
        }
    }
}
