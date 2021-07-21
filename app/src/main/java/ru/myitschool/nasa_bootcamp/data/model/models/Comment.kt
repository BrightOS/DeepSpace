package ru.myitschool.nasa_bootcamp.data.model.models

class Comment(val comment: String, val userId: String, val date: Long) {
    constructor() : this("", "", -1)

    override fun toString(): String {
        return "Comment: $comment;\nUserId: $userId\nDate: $date\n\n"
    }
}