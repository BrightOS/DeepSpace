package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import java.lang.Exception
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

    suspend fun getLikeCount(postId: Int): Long {
        return fDb.getReference("posts").child(postId.toString()).child("likes").get()
            .await().childrenCount
    }

    suspend fun checkIfHasLike(postId: Int): Boolean {
        return fDb.getReference("posts").child(postId.toString()).child("likes").get().await()
            .hasChild(userInstance.uid!!)
    }

    override suspend fun pushComment(postId: Int, comment: String) {
        errors = if (userInstance.uid != null) {
            val commentObject =
                Comment(getCommentCount(postId), comment, userInstance.uid.toString(), Date().time)
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(getCommentCount(postId).toString()).setValue(commentObject).await()
            ""
        } else {
            "User is not authenticated"
        }
    }

    override suspend fun deleteComment(postId: Int, commentId: Long) {
        errors = if (userInstance.uid != null) {
            try {
                fDb.getReference("posts").child(postId.toString()).child("comments")
                    .child(commentId.toString()).removeValue().await()
                ""
            } catch (e: Exception) {
                "Comment doesn't exist"
            }
        } else {
            "User is not authenticated"
        }
    }

    override suspend fun pushLike(postId: Int) {
        errors = if (userInstance.uid != null && !checkIfHasLike(postId)) {
            fDb.getReference("posts").child(postId.toString()).child("likes").child(userInstance.uid!!)
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