package ru.myitschool.nasa_bootcamp.ui.home.social_media

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.myitschool.nasa_bootcamp.data.dto.firebase.CommentDto
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.data.repository.NetworkRepository
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SocialMediaViewModelImpl @Inject constructor(private val networkRepository: NetworkRepository) :
    SocialMediaViewModel, ViewModel() {
    private val blogs =
        MutableLiveData<Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>>>()
    private val articles =
        MutableLiveData<Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>>>()
    private var selectedPost: ContentWithLikesAndComments<PostModel>? = null
    private var selectedArticle: ContentWithLikesAndComments<ArticleModel>? = null

    private val onArticleModelUpdated = MutableLiveData<Data<out ContentWithLikesAndComments<ArticleModel>>>()

    override fun getBlogs() = blogs
    override fun getNews() = articles

    override suspend fun loadBlogs() {
        blogs.postValue(networkRepository.getBlogPosts())
    }

    override suspend fun loadNews() {
        articles.postValue(networkRepository.getNews())
    }

    override fun getViewModelScope() = viewModelScope

    override fun setSelectedPost(post: ContentWithLikesAndComments<PostModel>) {
        selectedPost = post
    }

    override fun getSelectedPost() = selectedPost

    override fun setSelectedArticle(article: ContentWithLikesAndComments<ArticleModel>) {
        selectedArticle = article
    }

    override fun getSelectedArticle() = selectedArticle

    override suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    ): Resource<Nothing> {
        return networkRepository.pressedLikeOnComment(item, comment)
    }

    override suspend fun pressedLikeOnItem(item: ContentWithLikesAndComments<out Any>): Resource<Nothing> {
        return networkRepository.pressedLikeOnItem(item)
    }

    override suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment?
    ): Resource<Nothing> {
        return networkRepository.sendComment(message, id, _class, parentComment)
    }

    override fun newsChildChangedListener(
        articleModel: ArticleModel
    ) {
        val dbInstance = FirebaseDatabase.getInstance()
        dbInstance.getReference("posts").child("ArticleModel").child(articleModel.id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comments: MutableList<Comment> = mutableListOf()
                    val likes: MutableList<UserModel> = mutableListOf()
                    viewModelScope.launch {
                        snapshot.child("comments").children.forEach {
                            val id = it.child("id").getValue(Long::class.java)
                            val text = it.child("comment").getValue(String::class.java)
                            val likes = mutableListOf<UserModel>()

                            it.child("likes").children.forEach { like ->
                                val userModel =
                                    networkRepository.getUser(like.getValue(String::class.java)!!)
                                likes.add(userModel!!)
                            }

                            val subComments = mutableListOf<SubComment>()

                            val author: UserModel = networkRepository.getUser(
                                it.child("userId").getValue(String::class.java).toString()
                            )!!

                            val date = it.child("date").getValue(Long::class.java)
                            comments.add(
                                Comment(id!!, text!!, likes, subComments, author, date!!)
                            )
                        }

                        snapshot.child("likes").children.forEach {
                            val userModel =
                                networkRepository.getUser(it.getValue(String::class.java)!!)
                            likes.add(userModel!!)
                        }

                        onArticleModelUpdated.postValue(Data.Ok(
                            ContentWithLikesAndComments<ArticleModel>(
                                articleModel,
                                likes,
                                comments
                            ))
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Firebase Error", error.message)
                }
            })
    }

    override fun getChildChangedObserver(): LiveData<Data<out ContentWithLikesAndComments<ArticleModel>>> = onArticleModelUpdated
}