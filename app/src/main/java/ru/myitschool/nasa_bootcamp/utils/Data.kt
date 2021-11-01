package ru.myitschool.nasa_bootcamp.utils


sealed class Data<out T>{
    object Loading : Data<Nothing>()
    data class Error(val message: String): Data<Nothing>()
    data class Ok<T>(val data: T): Data<T>()
    data class Local<T>(val data: T): Data<T>()
}
