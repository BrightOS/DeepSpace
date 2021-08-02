package ru.myitschool.nasa_bootcamp.data.model

open class Comment(
    val id: Long,
    val text: String,
    val likes: List<UserModel>,
    val subComments: List<SubComment>,
    val author: UserModel,
    val date: Long
) {
    constructor() : this(
        -1,
        "",
        listOf(),
        listOf(),
        UserModel(id = 1, avatarUrl = "", name = "Jason"),
        -1L
    )

    override fun toString(): String {
        return "ID: $id\nComment: $text;\nUserId: ${author.id}\nDate: $date\nLikes: $likes\nComments: $subComments"
    }
}