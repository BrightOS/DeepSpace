package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import java.util.*
import kotlin.collections.ArrayList

class CommentsViewModelImpl : ViewModel(), CommentsViewModel {
    private val fDb: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val userInstance: FirebaseAuth = FirebaseAuth.getInstance()
    var comments: MutableLiveData<ArrayList<Comment?>> = MutableLiveData()
    var likes: MutableLiveData<Int> = MutableLiveData()
    var errors: String = ""
    var isSuccess: Boolean = true

    private suspend fun getCommentCount(postId: Int): Long {
        return fDb.getReference("posts").child(postId.toString()).child("comments").get()
            .await().childrenCount + 1
    }

    override suspend fun pushComment(postId: Int, comment: String) {
        if (userInstance.uid != null) {
            val commentObject = Comment(comment, userInstance.uid.toString(), Date().time)
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(getCommentCount(postId).toString()).setValue(commentObject).await()
            isSuccess = true
            errors = ""
        } else {
            isSuccess = false
            errors = "User is not authenticated"
        }
    }

    override suspend fun getComments(postId: Int) {
        val result =
            fDb.getReference("posts").child(postId.toString()).child("comments").get().await()
        var list: ArrayList<Comment?> = ArrayList()
        for (comment in result.children) {
            list.add(comment.getValue(Comment::class.java))
        }
        comments.value = list
        isSuccess = true
        errors = ""
    }

    @ExperimentalCoroutinesApi
    override suspend fun listenForComments(postId: Int) {
        callbackFlow {
            val commentRef = fDb.getReference("posts").child(postId.toString()).child("comments")
            val listener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    comments.value?.add(snapshot.getValue(Comment::class.java))
                    isSuccess = true
                    errors = ""
                    trySend(snapshot)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    isSuccess = false
                    errors = error.message
                    close(error.toException())
                }
            }
            commentRef.addChildEventListener(listener)
            awaitClose { commentRef.removeEventListener(listener) }
        }
    }
}