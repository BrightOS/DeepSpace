package ru.myitschool.nasa_bootcamp.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.models.Comment
import java.util.*
import kotlin.collections.ArrayList

class CommentsViewModelImpl : ViewModel(), CommentsViewModel {
    private val fDb: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val userInstance: FirebaseAuth = FirebaseAuth.getInstance()
    private var commentList: ArrayList<Comment?> = ArrayList()
    var comments: MutableLiveData<ArrayList<Comment?>> = MutableLiveData()
    var likes: MutableLiveData<Int> = MutableLiveData()
    var errors: String = ""

    private suspend fun getCommentCount(postId: Int): Long {
        return fDb.getReference("posts").child(postId.toString()).child("comments").get()
            .await().childrenCount + 1
    }

    override suspend fun pushComment(postId: Int, comment: String) {
        if (userInstance.uid != null) {
            val commentObject = Comment(comment, userInstance.uid.toString(), Date().time)
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .child(getCommentCount(postId).toString()).setValue(commentObject).await()
            errors = ""
        } else {
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
        errors = ""
    }

    @ExperimentalCoroutinesApi
    override fun listenForComments(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fDb.getReference("posts").child(postId.toString()).child("comments")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        println("add")
                        commentList.add(snapshot.getValue(Comment::class.java))
                        comments.postValue(commentList)
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        // comments.value?
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
}