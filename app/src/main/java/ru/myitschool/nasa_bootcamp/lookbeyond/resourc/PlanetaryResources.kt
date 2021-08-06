package ru.myitschool.nasa_bootcamp.lookbeyond.resourc

import android.graphics.Bitmap
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord


interface PlanetaryResources {
    val lines: List<LineRes>
    val images: List<ImageRes>
}
interface LocationRes {
    val location: GeocentricCoord?
}

interface LineRes  {
    val lineWidth: Float
    val vertexGeocentric: List<GeocentricCoord>
}

interface ImageRes : LocationRes {
    val image: Bitmap?
    val verticalCorner: DoubleArray?
    val horizontalCorner: DoubleArray?
}

interface InitialResource {
    fun create(): PlanetaryResources
 }

