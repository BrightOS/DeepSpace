package ru.myitschool.nasa_bootcamp.data.model

import android.net.Uri

open class Comment(
    val id: Long,
    val text: String,
    val likes: List<UserModel>,
    val subComments: List<SubComment>,
    val author: UserModel,
    val date: Long
) {

    override fun toString(): String {
        return "ID: $id\nComment: $text;\nUserId: ${author.id}\nDate: $date\nLikes: $likes\nComments: $subComments"
    }
}