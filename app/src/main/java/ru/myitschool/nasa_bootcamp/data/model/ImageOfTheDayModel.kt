package ru.myitschool.nasa_bootcamp.data.model

/*
 * @author Yana Glad
 */
data class ImageOfTheDayModel(
    val date: String, //Дата
    val explanation: String, //Описание
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String, //Заголовок
    val url: String, //Ссылка на изображение
)
