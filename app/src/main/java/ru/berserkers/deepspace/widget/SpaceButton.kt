package ru.berserkers.deepspace.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.card.MaterialCardView
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.LayoutSpaceButtonBinding
import ru.berserkers.deepspace.utils.DimensionsUtil
import ru.berserkers.deepspace.utils.getColorFromAttributes

/*
 * @author Denis Shaikhlbarin
 */
class SpaceButton constructor(
    cont: Context,
    attrs: AttributeSet?,
) :
    MaterialCardView(cont, attrs) {
    private val binding: LayoutSpaceButtonBinding =
        LayoutSpaceButtonBinding.inflate(LayoutInflater.from(context), this)

    companion object {
        const val RADIUS_DP = 16
    }

    var text: String?
        set(value) {
            binding.buttonText.text = value
        }
        get() = binding.buttonText.text.toString()

    init {

        radius = DimensionsUtil.dpToPx(context, RADIUS_DP).toFloat()
        backgroundTintList = ColorStateList.valueOf(
            getColorFromAttributes(
                context,
                R.attr.buttonBackgroundColor
            )
        )

        isClickable = true
        isFocusable = true

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpaceButton)

            binding.buttonText.text = typedArray.getString(R.styleable.SpaceButton_text)
            binding.buttonText.isAllCaps =
                typedArray.getBoolean(R.styleable.SpaceButton_textAllCaps, false)
            binding.buttonText.setTextColor(
                typedArray.getColor(
                    R.styleable.SpaceButton_textColor,
                    Color.White.toArgb()
                )
            )

            typedArray.recycle()
        }
    }
}
