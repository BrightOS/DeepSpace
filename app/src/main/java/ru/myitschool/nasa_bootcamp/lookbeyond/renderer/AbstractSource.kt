package ru.myitschool.nasa_bootcamp.lookbeyond.renderer


import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord


abstract class AbstractSource protected constructor(override val location: GeocentricCoord, val color: Int)
    : LocationRes {
    protected constructor(color: Int) : this(GeocentricCoord.getInstance(0.0, 0.0), color)

}