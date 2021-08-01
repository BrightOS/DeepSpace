package ru.myitschool.nasa_bootcamp.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.layout_space_button.view.*
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.Extensions

class SpaceButton constructor(
    cont: Context,
    attrs: AttributeSet?,
) :
    MaterialCardView(cont, attrs) {

    var text: String?
        set(value) {
            button_text?.text = value
        }
        get() = button_text?.text?.toString()

    init {
        View.inflate(context, R.layout.layout_space_button, this)

        radius = DimensionsUtil.dpToPx(context, 16).toFloat()
        setBackgroundTintList(
            ColorStateList.valueOf(
                Extensions.getColorFromAttributes(
                    context,
                    R.attr.buttonBackgroundColor
                )
            )
        )

        isClickable = true
        isFocusable = true

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpaceButton)

            button_text?.text = typedArray.getString(R.styleable.SpaceButton_text)
            button_text?.isAllCaps =
                typedArray.getBoolean(R.styleable.SpaceButton_textAllCaps, false)

            typedArray.recycle()
        }
    }
}
