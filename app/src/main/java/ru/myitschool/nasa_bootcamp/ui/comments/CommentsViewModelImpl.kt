package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import ru.myitschool.nasa_bootcamp.data.model.SubComment
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CommentsViewModelImpl @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel(), CommentsViewModel {

    override suspend fun pushComment(source: String, postId: Int, comment: String): LiveData<Data<out String>> {
        return repository.pushComment(source, postId, comment)
    }

    override suspend fun pushSubComment(source: String, postId: Int, fatherCommentId: Long, comment: String): LiveData<Data<out String>> {
        return repository.pushSubComment(source, postId, fatherCommentId, comment)
    }

    override suspend fun deleteComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>> {
        return repository.deleteComment(source, postId, commentId)
    }

    override suspend fun deleteSubComment(source: String, postId: Int, fatherCommentId: Long, subCommentId: Long): LiveData<Data<out String>> {
        return repository.deleteSubComment(source, postId, fatherCommentId, subCommentId)
    }

    override suspend fun pushLike(source: String, postId: Int): LiveData<Data<out String>> {

    }

    override suspend fun pushLikeForComment(source: String, postId: Int, commentId: Long): LiveData<Data<out String>> {
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
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
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

    override suspend fun deleteLike(source: String, postId: Int): LiveData<Data<out String>> {
        errors = if (userInstance.uid != null && checkIfHasLike(postId)) {
            fDb.getReference("posts").child(postId.toString()).child("likes")
                .child(userInstance.uid!!).removeValue().await()
            ""
        } else {
            "User is not authenticated or he didn't`t like this post"
        }
    }

    override suspend fun deleteCommentLike(source: String, postId: Int, commentId: Long): LiveData<Data<out String>> {
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
        source: String,
        postId: Int,
        fatherCommentId: Long,
        subCommentId: Long
    ): LiveData<Data<out String>> {
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