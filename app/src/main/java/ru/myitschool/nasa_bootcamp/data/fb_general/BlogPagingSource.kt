package ru.myitschool.nasa_bootcamp.data.fb_general

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepository
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

class BlogPagingSource(private val firebaseRepository: FirebaseRepository) :
    PagingSource<DataSnapshot, LiveData<ContentWithLikesAndComments<PostModel>>>() {

    override suspend fun load(params: LoadParams<DataSnapshot>):
            LoadResult<DataSnapshot, LiveData<ContentWithLikesAndComments<PostModel>>> {
        return try {
            Log.d("HELP", "load: ${params.key}")
            val currentPage =
                params.key ?: FirebaseDatabase.getInstance().getReference("user_posts")
                    .orderByKey().limitToLast(10).get()
                    .await()
            val lastVisibleContent = currentPage.children.first().key.toString()
            val nextPage =
                FirebaseDatabase.getInstance().getReference("user_posts").orderByKey().endBefore(lastVisibleContent).limitToLast(10).get()
                    .await()
            val postsResource = firebaseRepository.getPostsFromDataSnapshot(currentPage)
            if (postsResource.status == Status.SUCCESS)
                LoadResult.Page(
                    data = postsResource.data!!,
                    prevKey = null,
                    nextKey = nextPage
                )
            else LoadResult.Error(Exception(postsResource.message.toString()))
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DataSnapshot, LiveData<ContentWithLikesAndComments<PostModel>>>): DataSnapshot? {
        return null
    }
}