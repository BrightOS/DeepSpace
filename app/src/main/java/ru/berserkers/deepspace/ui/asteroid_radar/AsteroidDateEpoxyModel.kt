package ru.berserkers.deepspace.ui.asteroid_radar

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.utils.KotlinEpoxyHolder
import ru.berserkers.deepspace.utils.dateToAmericanFormat

/*
 * @author Denis Shaikhlbarin
 */
@EpoxyModelClass(layout = R.layout.date_item)
abstract class AsteroidDateEpoxyModel :
    EpoxyModelWithHolder<AsteroidDateEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var date: String

    override fun bind(holder: Holder) {
        holder.time.text = dateToAmericanFormat(date)
    }

    inner class Holder : KotlinEpoxyHolder() {
        val time by bind<TextView>(R.id.time)
    }
}
