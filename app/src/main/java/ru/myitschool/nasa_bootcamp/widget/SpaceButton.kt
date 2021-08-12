package ru.myitschool.nasa_bootcamp.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.card.MaterialCardView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.LayoutSpaceButtonBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.getColorFromAttributes


class SpaceButton constructor(
    cont: Context,
    attrs: AttributeSet?,
) :
    MaterialCardView(cont, attrs) {
    private val binding: LayoutSpaceButtonBinding =
        LayoutSpaceButtonBinding.inflate(LayoutInflater.from(cont), this)

    var text: String?
        set(value) {
            binding.buttonText.text = value
        }
        get() = binding.buttonText.text.toString()

    init {

        radius = DimensionsUtil.dpToPx(context, 16).toFloat()
        setBackgroundTintList(
            ColorStateList.valueOf(
                getColorFromAttributes(
                    context,
                    R.attr.buttonBackgroundColor
                )
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
