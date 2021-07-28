package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.Post

class CreatePostViewModelImpl : ViewModel(), CreatePostViewModel {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var dbReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("posts")

    override suspend fun createPost(post: Post) {
        dbReference.child(getLastPostId()).setValue(post).await()
    }

    override suspend fun loadImage(postId: String, imageId: Int, imagePath: Uri) {
        val storageRef = storage.getReference("posts/$postId/$imageId")
        storageRef.putFile(imagePath).await()
    }

    override fun getLastPostId(): String {
        var count: Long = 0
        dbReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                count = snapshot.childrenCount
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return count.toString()
    }
}