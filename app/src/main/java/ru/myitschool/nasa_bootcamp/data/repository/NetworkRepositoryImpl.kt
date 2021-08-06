package ru.myitschool.nasa_bootcamp.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>> {
        val newsList = mutableListOf<LiveData<ContentWithLikesAndComments<ArticleModel>>>()
        for (article in newsRepository.getNews().data.orEmpty()) {
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
        //val allPosts = firebaseRepository.getAllPosts()
        return Resource.error("TO DO", null)
    }

    override suspend fun pressedLikeOnItem(
        item: ContentWithLikesAndComments<out Any>
    ): Resource<Nothing> {
        try {
            if (item.content::class.java == ArticleModel::class.java) {
                firebaseRepository.pushLike(
                    "ArticleModel",
                    (item.content as ArticleModel).id.toInt()
                )!!
            } else {
                firebaseRepository.pushLike("UserPost", (item.content as ArticleModel).id.toInt())!!
            }
        }
        catch (e: Exception) {
            return Resource.error(e.message.toString(), null)
        }
        return Resource.success(null)
    }

    override suspend fun pressedLikeOnComment(item: ContentWithLikesAndComments<out Any>, comment: Comment): Resource<Nothing> {
        try{
            if (item.content::class.java == ArticleModel::class.java) {
                firebaseRepository.pushLikeForComment("ArticleModel", (item.content as ArticleModel).id.toInt(), comment.id)
            } else {
                firebaseRepository.pushLikeForComment("UserPost", (item.content as ArticleModel).id.toInt(), comment.id)
            }
        }
        catch (e: Exception) {
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
            firebaseRepository.pushComment("ArticleModel", id.toInt(), message)
        } else {
            firebaseRepository.pushComment("UserPost", id.toInt(), message)
        }
        return Resource.success(null)
    }

    override suspend fun getUser(uid: String): UserModel? {
        return firebaseRepository.getUser(uid)
    }

    override suspend fun getCurrentUser(): UserModel? = firebaseRepository.getCurrentUser()
}

