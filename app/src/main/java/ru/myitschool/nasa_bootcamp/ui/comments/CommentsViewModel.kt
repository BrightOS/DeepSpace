package ru.myitschool.nasa_bootcamp.ui.comments

import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow

interface CommentsViewModel {
    suspend fun pushComment(postId: Int, comment: String)
    suspend fun deleteComment(postId: Int, commentId: Long)
    suspend fun pushLike(postId: Int)
    suspend fun deleteLike(postId: Int)
    fun listenForComments(postId: Int)
    fun listenForLikes(postId: Int)
}