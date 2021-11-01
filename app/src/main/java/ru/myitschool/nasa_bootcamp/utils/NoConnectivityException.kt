package ru.myitschool.nasa_bootcamp.utils

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet connection"
}
