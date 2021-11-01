package ru.myitschool.nasa_bootcamp.data.model

/*
 * @author Vladimir Abubakirov
 */
open class Comment(
    val id: Long,
    val text: String,
    val likes: List<UserModel>,
    var subComments: List<SubComment>,
    val author: UserModel,
    val date: Long
) {

    override fun toString(): String {
        return "ID: $id\nComment: $text;\nUserId: ${author}\nDate: $date\nLikes: $likes\nComments: $subComments"
    }
}