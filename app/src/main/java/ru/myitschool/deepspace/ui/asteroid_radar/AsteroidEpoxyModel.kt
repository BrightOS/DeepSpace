package ru.myitschool.deepspace.ui.asteroid_radar

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.card.MaterialCardView
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.utils.KotlinEpoxyHolder

/*
 * @author Denis Shaikhlbarin
 */
@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.asteroid_item)
abstract class AsteroidEpoxyModel(
    val context: Context
) : EpoxyModelWithHolder<AsteroidEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var estimatedDiameter: String

    @EpoxyAttribute
    var absoluteMagnitude: Double = 0.0

    @EpoxyAttribute
    var speed: Double = 0.0

    @EpoxyAttribute
    var distanceFromEarth: Double = 0.0

    @EpoxyAttribute
    open var potencialDanger: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.asteroidName.text = name
        holder.distanceFromEarth.text = "%.6f AU".format(distanceFromEarth)
        holder.estimatedDiameter.text = estimatedDiameter

        when (potencialDanger) {

            true -> {
                holder.dangerous.text = "It's dangerous :("
                holder.friendlyIcon.setImageResource(R.drawable.ic_close_square)
                ContextCompat.getColor(
                    context,
                    R.color.red
                ).let {
                    holder.friendlyIcon.setColorFilter(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        holder.asteroidCard.outlineSpotShadowColor = it
                        holder.asteroidCard.outlineAmbientShadowColor = it
                    }
                }
            }

            false -> {
                holder.dangerous.text = "It's friendly :)"
                holder.friendlyIcon.setImageResource(R.drawable.ic_tick_square)

                ContextCompat.getColor(
                    context,
                    R.color.green
                ).let {
                    holder.friendlyIcon.setColorFilter(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        holder.asteroidCard.outlineSpotShadowColor = it
                        holder.asteroidCard.outlineAmbientShadowColor = it
                    }
                }
            }

        }
    }

    inner class Holder : KotlinEpoxyHolder() {
        val asteroidName by bind<TextView>(R.id.asteroid_name)
        val estimatedDiameter by bind<TextView>(R.id.diameter)
        val distanceFromEarth by bind<TextView>(R.id.distance)
        val asteroidCard by bind<MaterialCardView>(R.id.asteroidCard)
        val dangerous by bind<TextView>(R.id.danger)
        val friendlyIcon by bind<ImageView>(R.id.friendly_icon)
    }
}