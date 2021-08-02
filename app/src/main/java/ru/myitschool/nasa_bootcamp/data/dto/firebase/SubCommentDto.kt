package ru.myitschool.nasa_bootcamp.data.dto.firebase

class SubCommentDto(val id: Long, val fatherId: Long, val comment: String, val likes: List<String>, val userId: String, val date: Long) {
    constructor() : this(-1, -1,"", listOf(), "", -1)

    override fun toString(): String {
        return "ID: $id\nFatherId: $fatherId\nComment: $comment\nLikes: $likes\nUserId: $userId\nDate:$date"
    }
}
