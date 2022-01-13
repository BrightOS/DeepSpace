package ru.berserkers.deepspace.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.*
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.LayoutLoadingBinding

/*
 * @author Denis Shaikhlbarin
 */
@OptIn(DelicateCoroutinesApi::class)
class SpaceLoadingView constructor(
    private val cont: Context,
    attrs: AttributeSet?,
) :
    ConstraintLayout(cont, attrs) {

    private val binding: LayoutLoadingBinding =
        LayoutLoadingBinding.inflate(LayoutInflater.from(context), this)
    private var errorText: CharSequence?
        set(value) {
            binding.errorText.text = value
        }
        get() = binding.errorText.text

    private var showCheckIcon: Boolean = false

    init {
        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpaceLoadingView)

            typedArray.getString(R.styleable.SpaceLoadingView_errorText)?.let {
                binding.errorText.text =
                    it.ifEmpty { cont.resources.getString(R.string.default_error) }

            }
            showCheckIcon = typedArray.getBoolean(R.styleable.SpaceLoadingView_checkEnabled, false)

            if (typedArray.getBoolean(R.styleable.SpaceLoadingView_showByDefault, false)) {
                prepareLoadingView()
                binding.loadingProgressBar.visibility = View.VISIBLE
                binding.loadingRoot.visibility = View.VISIBLE
                GlobalScope.launch {
                    delay(DEFAULT_SPACE_LOAD_DELAY)
                    MainScope().launch {
                        binding.loadingProgressBar.indeterminateMode = true
                    }
                }
            }

            typedArray.recycle()
        }
    }

    fun startLoadingAnimation() {
        MainScope().launch {
            prepareLoadingView()
            binding.loadingProgressBar.visibility = View.VISIBLE

            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, false)
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup, sharedAxis)

            binding.loadingRoot.visibility = View.VISIBLE
        }
    }

    fun stopLoadingAnimation(
        showCheckIcon: Boolean = this.showCheckIcon,
    ) {
        MainScope().launch {
            if (showCheckIcon) {
                var sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                TransitionManager.beginDelayedTransition(binding.loadingRoot, sharedAxis)

                binding.loadingProgressBar.visibility = View.GONE
                binding.donePic.visibility = View.VISIBLE

                GlobalScope.launch {
                    delay(DELAY)
                    MainScope().launch {
                        sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                        TransitionManager.beginDelayedTransition(
                            binding.root as ViewGroup,
                            sharedAxis
                        )

                        binding.loadingRoot.visibility = View.GONE
                    }
                }
            } else {
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup, sharedAxis)

                binding.loadingRoot.visibility = View.GONE
            }
        }
    }

    fun showError(
        errorText: String = this.errorText as String,
    ) {
        MainScope().launch {
            var sharedAxis: MaterialSharedAxis

            binding.errorText.text = errorText

            if (binding.loadingRoot.visibility == View.GONE) {
                prepareLoadingView()

                binding.errorPic.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE

                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup, sharedAxis)
                binding.loadingRoot.visibility = View.VISIBLE
            } else {
                sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
                TransitionManager.beginDelayedTransition(binding.loadingRoot, sharedAxis)
                binding.loadingProgressBar.visibility = View.GONE
                binding.errorPic.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
            }

            GlobalScope.launch {
                delay(DELAY)
                MainScope().launch {
                    sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                    TransitionManager.beginDelayedTransition(binding.root as ViewGroup, sharedAxis)
                    binding.loadingRoot.visibility = View.GONE
                }
            }
        }
    }

    private fun prepareLoadingView() {
        binding.loadingProgressBar.visibility = View.GONE
        binding.errorPic.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.donePic.visibility = View.GONE
    }

    companion object {
        const val DELAY = 1000L
        const val DEFAULT_SPACE_LOAD_DELAY = 200L
    }
}
