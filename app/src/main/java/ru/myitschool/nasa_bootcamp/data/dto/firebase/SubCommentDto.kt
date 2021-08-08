package ru.myitschool.nasa_bootcamp.data.dto.firebase

class SubCommentDto(val id: Long, val text: String, val likes: List<UserDto>, val userId: UserDto, val date: Long) {
    constructor() : this(-1, "", listOf(), UserDto(), -1,)

    override fun toString(): String {
        return "ID: $id\nComment: $text\nLikes: $likes\nUserId: $userId\nDate:$date"
    }
}
