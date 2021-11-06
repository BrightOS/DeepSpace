package ru.myitschool.deepspace.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize - DimensionsUtil.dpToPx(view.context, 2)
            }
            left = spaceSize
            right = spaceSize
            bottom = spaceSize - DimensionsUtil.dpToPx(view.context, 2)
        }
    }
}
