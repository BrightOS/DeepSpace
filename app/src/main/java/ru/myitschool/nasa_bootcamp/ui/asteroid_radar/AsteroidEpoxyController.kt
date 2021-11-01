package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyController
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel

/*
 * @author Denis Shaikhlbarin
 */
class AsteroidEpoxyController(
    val context: Context
) : EpoxyController() {

    val data: MutableList<AsteroidModel> = mutableListOf()
    private val isSticky: MutableList<Boolean> = mutableListOf()
    private var currentDate = ""

    fun setData(data: MutableList<AsteroidModel>?) {
        this.data.clear()

        addData(data)
    }

    private fun addData(data: MutableList<AsteroidModel>?) {
        if (data != null) {
            this.data.addAll(data)

            var previousDate = ""
            data.forEach {
                if (it.date != previousDate) {
                    previousDate = it.date
                    isSticky.add(true)
                }
                isSticky.add(false)
            }
        }

        requestModelBuild()
    }

    override fun isStickyHeader(position: Int) = isSticky[position]

    override fun setupStickyHeaderView(stickyHeader: View) {
        super.setupStickyHeaderView(stickyHeader)
        stickyHeader.elevation = 15000f
    }

    override fun teardownStickyHeaderView(stickyHeader: View) {
        super.teardownStickyHeaderView(stickyHeader)
        stickyHeader.elevation = 0f
    }

    override fun buildModels() {
        if (data.size > 0) {
            var dateID = -1
            val list = data.toList()

            list.forEach {
                if (it.date != currentDate) {
                    asteroidDate {
                        id(Long.MAX_VALUE - dateID)
                        dateID++
                        date(it.date)
                    }
                    currentDate = it.date
                }

                asteroid(context) {
                    id(it.id)
                    name(it.name)
                    estimatedDiameter(it.estimatedDiameter)
                    absoluteMagnitude(it.absoluteMagnitude)
                    speed(it.speed)
                    distanceFromEarth(it.distanceFromEarth)
                    potencialDanger(it.potencialDanger)
                }
            }
        }
    }
}
