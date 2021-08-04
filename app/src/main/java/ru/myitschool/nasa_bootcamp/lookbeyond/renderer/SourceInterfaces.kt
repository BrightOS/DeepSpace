package ru.myitschool.nasa_bootcamp.lookbeyond.renderer

import android.graphics.Bitmap
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord
import kotlin.collections.ArrayList



interface ImageRes : LocationRes {
    val image: Bitmap
    val verticalCorner: DoubleArray
    val horizontalCorner: DoubleArray
 }

interface LineSource {
    val lineWidth: Float
    fun vertexGeocentric(): ArrayList<GeocentricCoord>

 }
interface LocationRes {
    val location: GeocentricCoord
}

interface InitialResource {
    fun initialize(): PlanetaryResources
 }

interface PlanetaryResources {
    fun lineResources(): List<LineSource>
    fun imagesResources(): List<ImageRes>
}


