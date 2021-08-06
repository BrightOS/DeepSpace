package ru.myitschool.nasa_bootcamp.lookbeyond.resourc

import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord


abstract class AbstractRes protected constructor(
    override val location: GeocentricCoord,
    val color: Int
) : LocationRes {

    var names: List<String>? = null

    protected constructor(color: Int) : this(
        GeocentricCoord.getInstance(0.0, 0.0),
        color
    )

}