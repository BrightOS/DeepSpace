package ru.myitschool.nasa_bootcamp.data.model


abstract class PostView(val id: Int, val type: Int)

class TextPost(private val _id: Int, private val _type: Int, val text: String) : PostView(_id, _type)

class ImagePost(private val _id: Int, private val _type: Int, val imagePath: String) : PostView(_id, _type)

data class Post(var title: String, var data: ArrayList<PostView>) {
    constructor() : this("", arrayListOf())
}