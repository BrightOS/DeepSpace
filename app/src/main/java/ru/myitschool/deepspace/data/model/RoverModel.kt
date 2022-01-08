package ru.myitschool.deepspace.data.model

import ru.myitschool.deepspace.data.dto.nasa.rovers.Camera
import ru.myitschool.deepspace.data.dto.nasa.rovers.Rover

class RoverModel(_id: Int, _img_src: String, _camera: Camera, _rover: Rover, _earth_date : String) {
    var id = _id
    var img_src = _img_src //фото с Марса
    val rover: Rover = _rover //Информация о Ровере
    val camera = _camera //Информация о камере
    val earth_date = _earth_date
}
