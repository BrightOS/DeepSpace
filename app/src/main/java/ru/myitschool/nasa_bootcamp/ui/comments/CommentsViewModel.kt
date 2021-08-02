package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow
import ru.myitschool.nasa_bootcamp.utils.Data

interface CommentsViewModel {
    suspend fun pushComment(source: String, postId: Int, comment: String): LiveData<Data<out String>>
    suspend fun pushSubComment(source: String, postId: Int, fatherCommentId: Long, comment: String): LiveData<Data<out String>>
    suspend fun deleteComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun deleteSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    suspend fun pushLike(source: String, postId: Int): LiveData<Data<out String>>
    suspend fun pushLikeForComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun pushLikeForSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    suspend fun deleteLike(source: String, postId: Int): LiveData<Data<out String>>
    suspend fun deleteCommentLike(source: String, postId: Int, commentId: Long): LiveData<Data<out String>>
    suspend fun deleteSubCommentLike(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>>
    fun listenForComments(source: String, postId: Int)
    fun listenForLikes(source: String, postId: Int):
}