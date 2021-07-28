package ru.myitschool.nasa_bootcamp.data.model


abstract class PostView(val id: Int)

class TextPost(private val _id: Int, val text: String) : PostView(_id)

class ImagePost(private val _id: Int, val imagePath: String) : PostView(_id)

data class Post(var title: String, var data: ArrayList<PostView>) {
    constructor() : this("", arrayListOf())
}