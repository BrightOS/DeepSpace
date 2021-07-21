package ru.myitschool.nasa_bootcamp.data.model

import com.example.firstkotlinapp.model.rovers.Camera
import com.example.firstkotlinapp.model.rovers.Rover

class RoverModel(_id: Int, _img_src: String, _camera: Camera, _rover: Rover) {
    var id = _id
    var img_src = _img_src //фото с Марса
    val rover: Rover = _rover //Информация о Ровере
    val camera = _camera //Информация о камере
}

