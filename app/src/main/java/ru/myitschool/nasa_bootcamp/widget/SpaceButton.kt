package ru.myitschool.nasa_bootcamp.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.layout_space_button.view.button_text
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.getColorFromAttributes

/*
 * @author Denis Shaikhlbarin
 */
class SpaceButton constructor(
    cont: Context,
    attrs: AttributeSet?,
) :
    MaterialCardView(cont, attrs) {

    companion object{
        const val RADIUS_DP = 16
    }

    var text: String?
        set(value) {
            button_text?.text = value
        }
        get() = button_text?.text?.toString()

    init {
        View.inflate(context, R.layout.layout_space_button, this)

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

            button_text?.text = typedArray.getString(R.styleable.SpaceButton_text)
            button_text?.isAllCaps =
                typedArray.getBoolean(R.styleable.SpaceButton_textAllCaps, false)
            button_text?.setTextColor(typedArray.getColor(R.styleable.SpaceButton_textColor, Color.White.toArgb()))

            typedArray.recycle()
        }
    }
}
