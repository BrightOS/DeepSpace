package ru.myitschool.deepspace.navigator.managers

import ru.myitschool.deepspace.navigator.control.VectorPointing


interface AbstractManager {
    var pointing: VectorPointing?
    fun start()
    fun stop()
}