package ru.myitschool.nasa_bootcamp.data.dto.firebase

class CommentDto(val id: Long, val comment: String, val likes: List<String>, val subComments: List<SubCommentDto>, val userId: String, val date: Long) {
    constructor() : this(-1, "", listOf(), listOf(), "", -1)

    override fun toString(): String {
        return "ID: $id\nComment: $comment;\nUserId: $userId\nDate: $date\nLikes: $likes\nComments: $subComments"
    }
}