package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.dto.firebase.CommentDto
import ru.myitschool.nasa_bootcamp.data.dto.firebase.SubCommentDto
import ru.myitschool.nasa_bootcamp.data.model.Comment
import java.util.*
import kotlin.collections.ArrayList

class CommentsViewModelImpl : ViewModel(), CommentsViewModel {
    private val fDb: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val userInstance: FirebaseAuth = FirebaseAuth.getInstance()
    private var commentList: ArrayList<Comment?> = ArrayList()
    var comments: MutableLiveData<ArrayList<Comment?>> = MutableLiveData()
    var likes: MutableLiveData<Long> = MutableLiveData()
    var errors: String = ""

    private suspend fun getCommentCount(postId: Int): Long {
        return fDb.getReference("posts").child(postId.toString()).child("comments").get()
            .await().childrenCount + 1
    }

    private suspend fun getSubCommentCount(postId: Int, fatherCommentId: Long): Long {
        return fDb.getReference("posts").child(postId.toString()).child("comments")
            .child(fatherCommentId.toString()).child("subComments").get()
            .await().childrenCount + 1
    }

    private suspend fun getLastCommentId(postId: Int): Long {
        return try {
            fDb.getReference("posts").child(postId.toString()).child("comments").get()
                .await().children.last().key!!.toLong()
        } catch (e: Exception) {
            1
        }
    }

    private suspend fun getLastSubCommentId(postId: Int, fatherCommentId: Long): Long {
        return try {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(fatherCommentId.toString()).child("subComments")
                .get()
                .await().children.last().key!!.toLong()
        } catch (e: Exception) {
            1
        }
    }

    suspend fun getLikeCount(postId: Int): Long {
        return fDb.getReference("posts").child(postId.toString()).child("likes").get()
            .await().childrenCount
    }

    suspend fun checkIfHasLike(postId: Int): Boolean {
        return fDb.getReference("posts").child(postId.toString()).child("likes").get().await()
            .hasChild(userInstance.uid!!)
    }

    suspend fun checkIfHasCommentLike(postId: Int, commentId: Long): Boolean {
        return fDb.getReference("posts").child(postId.toString()).child("comments")
            .child(commentId.toString()).child("likes").get().await()
            .hasChild(userInstance.uid!!)
    }

    suspend fun checkIfHasSubCommentLike(
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): Boolean {
        return fDb.getReference("posts").child(postId.toString()).child("comments")
            .child(fatherCommentId.toString()).child("subComments").child(subCommentId.toString())
            .child("likes").get().await()
            .hasChild(userInstance.uid!!)
    }

    suspend fun getCommentAuthor(postId: Int, commentId: Long): String? {
        return fDb.getReference("posts").child(postId.toString()).child("comments")
            .child(commentId.toString()).child("userId").get().await().getValue(String::class.java)
    }

    suspend fun getSubCommentAuthor(
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): String? {
        return fDb.getReference("posts").child(postId.toString()).child("comments")
            .child(fatherCommentId.toString()).child("subComments").child(subCommentId.toString())
            .child("userId").get().await().getValue(String::class.java)
    }

    override suspend fun pushComment(postId: Int, comment: String) {
        errors = if (userInstance.uid != null) {
            val commentId = getLastCommentId(postId) + 1
            val commentObject =
                CommentDto(
                    commentId,
                    comment,
                    listOf(),
                    listOf(),
                    userInstance.uid!!,
                    Date().time
                )
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(commentId.toString()).setValue(commentObject).await()
            ""
        } else {
            "User is not authenticated"
        }
    }

    override suspend fun pushSubComment(postId: Int, fatherCommentId: Long, comment: String) {
        errors = if (userInstance.uid != null) {
            val subCommentId = getLastSubCommentId(postId, fatherCommentId) + 1
            val subCommentObject = SubCommentDto(
                subCommentId, fatherCommentId, comment, listOf(),
                userInstance.uid!!, Date().time
            )
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(fatherCommentId.toString()).child("subComments")
                .child(subCommentId.toString())
                .setValue(subCommentObject).await()
            ""
        } else {
            "User is not authenticated"
        }
    }

    override suspend fun deleteComment(postId: Int, commentId: Long) {
        errors = if (userInstance.uid != null && userInstance.uid == getCommentAuthor(
                postId,
                commentId
            )
        ) {
            try {
                fDb.getReference("posts").child(postId.toString()).child("comments")
                    .child(commentId.toString()).removeValue().await()
                ""
            } catch (e: Exception) {
                "Comment doesn't exist"
            }
        } else {
            "User is not authenticated or he is not author of the comment"
        }
    }

    override suspend fun deleteSubComment(postId: Int, fatherCommentId: Long, subCommentId: Long) {
        errors = if (userInstance.uid != null && userInstance.uid == getSubCommentAuthor(
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            try {
                fDb.getReference("posts").child(postId.toString()).child("comments")
                    .child(fatherCommentId.toString()).child("subComments")
                    .child(subCommentId.toString()).removeValue().await()
                ""
            } catch (e: Exception) {
                "SubComment doesn't exist"
            }
        } else {
            "User is not authenticated or he is not author of the SubComment"
        }
    }

    override suspend fun pushLike(postId: Int) {
        errors = if (userInstance.uid != null && !checkIfHasLike(postId)) {
            fDb.getReference("posts").child(postId.toString()).child("likes")
                .child(userInstance.uid!!)
                .setValue(userInstance.uid).await()
            ""
        } else {
            "User is not authenticated or already has a like"
        }
    }

    override suspend fun pushLikeForComment(postId: Int, commentId: Long) {
        errors = if (userInstance.uid != null && !checkIfHasCommentLike(postId, commentId)) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(commentId.toString()).child("likes").child(userInstance.uid!!)
                .setValue(userInstance.uid).await()
            ""
        } else {
            "User is not authenticated or already has a like"
        }
    }

    override suspend fun pushLikeForSubComment(
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ) {
        errors = if (userInstance.uid != null && !checkIfHasSubCommentLike(
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(fatherCommentId.toString()).child("subComments")
                .child(subCommentId.toString()).child("likes").child(userInstance.uid!!)
                .setValue(userInstance.uid).await()
            ""
        } else {
            "User is not authenticated or already has a like"
        }
    }

    override suspend fun deleteLike(postId: Int) {
        errors = if (userInstance.uid != null && checkIfHasLike(postId)) {
            fDb.getReference("posts").child(postId.toString()).child("likes")
                .child(userInstance.uid!!).removeValue().await()
            ""
        } else {
            "User is not authenticated or he didn't`t like this post"
        }
    }

    override suspend fun deleteCommentLike(postId: Int, commentId: Long) {
        errors = if (userInstance.uid != null && checkIfHasCommentLike(postId, commentId)) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(commentId.toString()).child("likes").child(userInstance.uid!!).removeValue()
                .await()
            ""
        } else {
            "User is not authenticated or he didn't`t like this comment"
        }
    }

    override suspend fun deleteSubCommentLike(
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ) {
        errors = if (userInstance.uid != null && checkIfHasSubCommentLike(
                postId,
                fatherCommentId,
                subCommentId
            )
        ) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(fatherCommentId.toString()).child("subComments")
                .child(subCommentId.toString()).child("likes").child(userInstance.uid!!)
                .removeValue()
                .await()
            ""
        } else {
            "User is not authenticated or he didn't`t like this subComment"
        }
    }

    override fun listenForComments(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .orderByChild("date")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        commentList.add(snapshot.getValue(Comment::class.java))
                        comments.postValue(commentList)
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {

                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        commentList.remove(snapshot.getValue(Comment::class.java))
                        comments.postValue(commentList)
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    }

                    override fun onCancelled(error: DatabaseError) {
                        errors = error.message
                    }

                })
        }
    }

    override fun listenForLikes(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fDb.getReference("posts").child(postId.toString()).child("likes")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        viewModelScope.launch(Dispatchers.IO) {
                            likes.postValue(getLikeCount(postId))
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        viewModelScope.launch(Dispatchers.IO) {
                            likes.postValue(getLikeCount(postId))
                        }
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    }

                    override fun onCancelled(error: DatabaseError) {
                        errors = error.message
                    }

                })
        }
    }
}