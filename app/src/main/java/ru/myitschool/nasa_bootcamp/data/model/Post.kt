package ru.myitschool.nasa_bootcamp.data.model


abstract class PostView(val _id: Int, val _type: Int)

class TextPost(private val id: Int, private val type: Int, val text: String) : PostView(id, type)

class ImagePost(private val id: Int, private val type: Int, val imagePath: String) : PostView(id, type)

data class Post(var title: String, var dateCreated: Long, var author: String, var data: ArrayList<PostView>?) {
    constructor() : this("", -1, "", arrayListOf())
}