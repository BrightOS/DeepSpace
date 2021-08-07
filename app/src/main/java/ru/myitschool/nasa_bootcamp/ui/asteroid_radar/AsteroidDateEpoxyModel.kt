package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.stickyheader.StickyHeaderCallbacks
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.utils.Extensions
import ru.myitschool.nasa_bootcamp.utils.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.date_item)
abstract class AsteroidDateEpoxyModel :
    EpoxyModelWithHolder<AsteroidDateEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var date: String

    override fun bind(holder: Holder) {
        holder.time.text = Extensions.dateToAmericanFormat(date)
    }

    inner class Holder : KotlinEpoxyHolder() {
        val time by bind<TextView>(R.id.time)
    }
}