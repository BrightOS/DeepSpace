package ru.myitschool.nasa_bootcamp.ui.comments

import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow

interface CommentsViewModel {
    suspend fun pushComment(postId: Int, comment: String)
    suspend fun pushSubComment(postId: Int, fatherCommentId: Long, comment: String)
    suspend fun deleteComment(postId: Int, commentId: Long)
    suspend fun deleteSubComment(postId: Int, fatherCommentId: Long, subCommentId: Long)
    suspend fun pushLike(postId: Int)
    suspend fun pushLikeForComment(postId: Int, commentId: Long)
    suspend fun pushLikeForSubComment(postId: Int, fatherCommentId: Long, subCommentId: Long)
    suspend fun deleteLike(postId: Int)
    suspend fun deleteCommentLike(postId: Int, commentId: Long)
    suspend fun deleteSubCommentLike(postId: Int, fatherCommentId: Long, subCommentId: Long)
    fun listenForComments(postId: Int)
    fun listenForLikes(postId: Int)
}