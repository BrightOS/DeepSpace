package ru.myitschool.nasa_bootcamp.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>> {

        return Resource.success(
            listOf(
                MutableLiveData(
                    ContentWithLikesAndComments(
                        likes = listOf(),
                        comments =
                        listOf(
                            Comment(
                                text = "ds/lkfsdfksdfsd",
                                subComments = listOf(),
                                id = 4,
                                author = UserModel(
                                    id = "4",
                                    avatarUrl = Uri.parse("https://lh3.googleusercontent.com/0xn6EsKc4lfdgFBt_1rA8uN6FgUUCrNO7cmTQny30x6wQhFrlTuZomwENpYsyMW00lytSuv6hLSHOs1voqpUautXcQ"),
                                    name = "Zach"
                                ),
                                likes = listOf(),
                                date = 1628008932
                            )
                        ),
                        content = ArticleModel(
                            id = 123,
                            title = "Hello World",
                            summary = "Hmm d;lfksd ;fkhsa ldkhf;s aldkhfl sdjfaskdhalsdl asdak  sdhlas khdlask dlkasdas ldkhas/ ldkha /sldkh a/skd a/skda skhdas",
                            imageUrl = "https://i.ytimg.com/vi/0QaX4KMjeVQ/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLDEYHd9BOxp4Qnw0gdFKQe2fNf1xg",
                            publishedAt = "2021-08-03T20:40:02.000Z",
                            url = "https://www.wikihow.com/Delete-an-Instagram-Account"
                        )
                    )
                )
            )
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

