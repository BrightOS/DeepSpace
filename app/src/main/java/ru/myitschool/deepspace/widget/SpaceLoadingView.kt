package ru.myitschool.deepspace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.layout_loading.view.*
import kotlinx.coroutines.*
import ru.myitschool.deepspace.R

/*
 * @author Denis Shaikhlbarin
 */
@DelicateCoroutinesApi
class SpaceLoadingView constructor(
    private val cont: Context,
    attrs: AttributeSet?,
) :
    ConstraintLayout(cont, attrs) {

    private var errorText: CharSequence?
        set(value) {
            error_text?.text = value
        }
        get() = error_text?.text

    private var showCheckIcon: Boolean = false

    init {
        View.inflate(context, R.layout.layout_loading, this)

        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpaceLoadingView)

            typedArray.getString(R.styleable.SpaceLoadingView_errorText)?.let {
                error_text?.text =
                    if (it.isNotEmpty())
                        it
                    else
                        cont.resources.getString(R.string.default_error)

            }
            showCheckIcon = typedArray.getBoolean(R.styleable.SpaceLoadingView_checkEnabled, false)

            if (typedArray.getBoolean(R.styleable.SpaceLoadingView_showByDefault, false)) {
                prepareLoadingView()
                loading_progress_bar.visibility = View.VISIBLE
                loading_root.visibility = View.VISIBLE
                GlobalScope.launch {
                    delay(DEFAULT_SPACE_LOAD_DELAY)
                    MainScope().launch {
                        loading_progress_bar.indeterminateMode = true
                    }
                }
            }

            typedArray.recycle()
        }
    }

    fun startLoadingAnimation() {
        MainScope().launch {
            prepareLoadingView()
            loading_progress_bar.visibility = View.VISIBLE

            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, false)
            rootView?.let {
                TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
            }

            loading_root.visibility = View.VISIBLE
        }
    }

    fun stopLoadingAnimation(
        showCheckIcon: Boolean = this.showCheckIcon,
    ) {
        MainScope().launch {
            if (showCheckIcon) {
                var sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                loading_root?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_progress_bar.visibility = View.GONE
                done_pic.visibility = View.VISIBLE

                GlobalScope.launch {
                    delay(DELAY)
                    MainScope().launch {
                        sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                        rootView?.let {
                            TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
                        }

                        loading_root.visibility = View.GONE
                    }
                }
            } else {
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                rootView?.let {
                    TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
                }

                loading_root.visibility = View.GONE
            }
        }
    }

    fun showError(
        errorText: String = this.errorText as String,
    ) {
        MainScope().launch {
            var sharedAxis: MaterialSharedAxis

            error_text.text = errorText

            if (loading_root.visibility == View.GONE) {
                prepareLoadingView()

                error_pic.visibility = View.VISIBLE
                error_text.visibility = View.VISIBLE

                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)

                rootView?.let {
                    TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
                }

                loading_root.visibility = View.VISIBLE
            } else {

                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                loading_root?.let {
                    TransitionManager.beginDelayedTransition(it, sharedAxis)
                }

                loading_progress_bar.visibility = View.GONE
                error_pic.visibility = View.VISIBLE
                error_text.visibility = View.VISIBLE
            }

            GlobalScope.launch {
                delay(DELAY)
                MainScope().launch {
                    sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                    rootView?.let {
                        TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
                    }
                    loading_root.visibility = View.GONE
                }
            }
        }
    }

    private fun prepareLoadingView() {
        loading_progress_bar.visibility = View.GONE
        error_pic.visibility = View.GONE
        error_text.visibility = View.GONE
        done_pic.visibility = View.GONE
    }

    companion object {
        const val DELAY = 1000L
        const val DEFAULT_SPACE_LOAD_DELAY = 200L
    }
}
