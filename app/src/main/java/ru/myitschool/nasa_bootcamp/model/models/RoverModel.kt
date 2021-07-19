package com.example.firstkotlinapp.model.models

import com.example.firstkotlinapp.model.Camera
import com.example.firstkotlinapp.model.Rover

class RoverModel(_id: Int, _img_src: String, _camera: Camera, _rover: Rover) {
    var id = _id
    var img_src = _img_src //фото с Марса
    val rover: Rover = _rover //Информация о Ровере
    val camera = _camera //Информация о камере
}

