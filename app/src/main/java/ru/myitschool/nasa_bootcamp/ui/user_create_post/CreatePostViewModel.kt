package ru.myitschool.nasa_bootcamp.ui.user_create_post

import ru.myitschool.nasa_bootcamp.data.model.Post

interface CreatePostViewModel {
    suspend fun createPost(post: Post)
}