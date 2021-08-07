package ru.myitschool.nasa_bootcamp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>> {
        val newsList = mutableListOf<LiveData<ContentWithLikesAndComments<ArticleModel>>>()
        val news = newsRepository.getNews()
        if (news.status == Status.ERROR) {
            return Resource.error(news.message!!, null)
        }
        for (article in news.data.orEmpty()) {
            val data = MutableLiveData<ContentWithLikesAndComments<ArticleModel>>()
            data.postValue(ContentWithLikesAndComments(article, listOf(), listOf()))
            firebaseRepository.articleModelEventListener(data)
            newsList.add(data)
        }
        return Resource.success(
            newsList
        )
    }

    override suspend fun getBlogPosts(): Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>> {
        return Resource.success(listOf())
    }

    override suspend fun pressedLikeOnItem(
        item: ContentWithLikesAndComments<out Any>
    ): Resource<Nothing> {
        try {
            if (item.content::class.java == ArticleModel::class.java) {
                val result = firebaseRepository.pushLike(
                    "ArticleModel",
                    (item.content as ArticleModel).id.toInt()
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            } else {
                val result = firebaseRepository.pushLike(
                    "UserPost",
                    (item.content as ArticleModel).id.toInt()
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            }
        } catch (e: Exception) {
            return Resource.error(e.message.toString(), null)
        }
        return Resource.success(null)
    }

    override suspend fun pressedLikeOnComment(
        item: ContentWithLikesAndComments<out Any>,
        comment: Comment
    ): Resource<Nothing> {
        try {
            if (item.content::class.java == ArticleModel::class.java) {
                val result = firebaseRepository.pushLikeForComment(
                    "ArticleModel",
                    (item.content as ArticleModel).id.toInt(),
                    comment.id
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            } else {
                val result = firebaseRepository.pushLikeForComment(
                    "UserPost",
                    (item.content as ArticleModel).id.toInt(),
                    comment.id
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            }
        } catch (e: Exception) {
            return Resource.error(e.message.toString(), null)
        }
        return Resource.success(null)
    }

    override suspend fun sendComment(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment?
    ): Resource<Nothing> {
        if (_class == ArticleModel::class.java) {
            val result = firebaseRepository.pushComment("ArticleModel", id.toInt(), message)
            if (result.status == Status.ERROR) {
                return Resource.error(result.message.toString(), null)
            }
        } else {
            val result = firebaseRepository.pushComment("UserPost", id.toInt(), message)
            if (result.status == Status.ERROR) {
                return Resource.error(result.message.toString(), null)
            }
        }
        return Resource.success(null)
    }

    override suspend fun getUser(uid: String): UserModel? {
        return firebaseRepository.getUser(uid)
    }

    override fun createPost(title: String, postItems: List<Any>): Resource<Nothing> {
        firebaseRepository.createPost(title, postItems)
        return Resource.error("TO DO", null)
    }

    override suspend fun getCurrentUser(): UserModel? = firebaseRepository.getCurrentUser()
}

