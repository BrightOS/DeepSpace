package ru.myitschool.nasa_bootcamp.ui.comments

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

interface CommentsViewModel {
    suspend fun pushComment(postId: Int, comment: String)
    suspend fun getComments(postId: Int)
    suspend fun listenForComments(postId: Int)
}