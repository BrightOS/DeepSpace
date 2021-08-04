package ru.myitschool.nasa_bootcamp.data.model

import android.net.Uri

class SubComment(
    id: Long,
    val fatherId: Long,
    text: String,
    likes: List<UserModel>,
    author: UserModel,
    date: Long
) : Comment(id, text, likes, listOf(), author, date) {
    constructor() : this(
        -1,
        -1,
        "",
        listOf(),
        UserModel(id = "", avatarUrl = Uri.EMPTY, name = "Jason"),
        -1
    )

    override fun toString(): String {
        return "ID: $id\nFatherId: $fatherId\nComment: $text\nLikes: $likes\nUserId: ${author.id}\nDate:$date"
    }
}