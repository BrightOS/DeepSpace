package ru.myitschool.deepspace.data.dto.firebase

import ru.myitschool.deepspace.data.model.UserModel


class CommentDto(val id: Long, val comment: String, val likes: List<UserModel>, val subComments: List<SubCommentDto>, val userId: UserDto, val date: Long) {
    constructor() : this(-1, "", listOf(), listOf(), UserDto(), -1)

    override fun toString(): String {
        return "ID: $id\nComment: $comment;\nUserId: $userId\nDate: $date\nLikes: $likes\nComments: $subComments"
    }
}