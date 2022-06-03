package ru.berserkers.deepspace.navigator.managers

import ru.berserkers.deepspace.navigator.pointing.VectorPointing

interface AbstractManager {
    var pointing: VectorPointing?
    fun start()
    fun stop()
}
