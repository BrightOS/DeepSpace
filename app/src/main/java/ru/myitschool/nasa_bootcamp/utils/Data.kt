package ru.myitschool.nasa_bootcamp.utils

sealed class Data<T>{
    data class Error(val message: String): Data<Nothing>()
    data class Ok<T>(val data: T): Data<T>()
}
