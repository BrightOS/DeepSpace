package ru.berserkers.deepspace.navigator.layers

import android.content.res.Resources
import ru.berserkers.deepspace.navigator.rendertype.ImageRun
import ru.berserkers.deepspace.navigator.rendertype.LineRun
import ru.berserkers.deepspace.navigator.thread.RendererExecutor
import ru.berserkers.deepspace.navigator.thread.RendererExecutor.RenderThread
import ru.berserkers.deepspace.navigator.thread.RendererTypeExecutorHelper.AbstractRenderType
import java.util.concurrent.locks.ReentrantLock


abstract class AbstractGlLayer(protected val resources: Resources) {
    private val locker = ReentrantLock()
    private var renderer: RendererExecutor? = null
    private var typeLineAbstract: AbstractRenderType<LineRun>? = null
    private var typeImageAbstract: AbstractRenderType<ImageRun>? = null

    enum class RenderResource {
        IMAGE, LINE
    }

    fun registerWithRenderer(executor: RendererExecutor) {
        renderer = executor
        runRedrawing()
    }

    abstract fun initialize()
    protected abstract fun runRedrawing()

    fun setVisible() {
        locker.lock()
        try {
            if (renderer == null) return
            renderer?.createThread()?.let { renderer!! run it }

        } finally {
            locker.unlock()
        }
    }

    protected fun render(thread: Runnable?) {
        renderer?.addRender(thread)
    }

    protected abstract fun redraw()

    protected fun redraw(
        lineRuns: ArrayList<LineRun>,
        imageRuns: ArrayList<ImageRun>,
    ) {
        if (renderer == null) return

        locker.lock()
        try {
            val thread = renderer?.createThread()
            thread.also {
                it?.let { setSources(lineRuns, RenderResource.LINE, it) }
                it?.let { setSources(imageRuns, RenderResource.IMAGE, it) }
                it?.let { renderer!! run it }
            }
        } finally {
            locker.unlock()
        }
    }

    private fun <E> setSources(
        sources: ArrayList<E>?,
        type: RenderResource, atomic: RenderThread,
    ) {
        var typeAbstract: AbstractRenderType<E>? = when (type) {
            RenderResource.LINE -> typeLineAbstract as AbstractRenderType<E>?
            RenderResource.IMAGE -> typeImageAbstract as AbstractRenderType<E>?
        }

        if (sources == null || sources.isEmpty()) {
            typeAbstract?.executeObjects(emptyList(), atomic)
            return
        }
        if (typeAbstract == null) {
            when (type) {
                RenderResource.LINE -> {
                    typeAbstract = atomic.createLineManager(50) as AbstractRenderType<E>
                    typeLineAbstract = typeAbstract as AbstractRenderType<LineRun>
                }
                RenderResource.IMAGE -> {
                    typeAbstract = atomic.createImageManager(100) as AbstractRenderType<E>
                    typeImageAbstract = typeAbstract as AbstractRenderType<ImageRun>
                }
            }
        }
        typeAbstract.executeObjects(sources, atomic)
    }
}
