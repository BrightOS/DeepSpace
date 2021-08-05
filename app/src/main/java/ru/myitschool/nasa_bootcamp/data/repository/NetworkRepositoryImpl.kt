package ru.myitschool.nasa_bootcamp.data.repository

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>> {

        return Resource.success(
            listOf(
                ContentWithLikesAndComments(
                    likes = MutableLiveData(Resource.success(listOf())),
                    id = 123,
                    comments = MutableLiveData(
                        Resource.success(
                            listOf(
                                MutableLiveData(
                                    Comment(
                                        text = "ds/lkfsdfksdfsd",
                                        subComments = listOf(),
                                        id = 4,
                                        author = UserModel(
                                            id = "4",
                                            avatarUrl = "https://lh3.googleusercontent.com/0xn6EsKc4lfdgFBt_1rA8uN6FgUUCrNO7cmTQny30x6wQhFrlTuZomwENpYsyMW00lytSuv6hLSHOs1voqpUautXcQ",
                                            name = "Zach"
                                        ),
                                        likes = listOf(),
                                        date = 1628008932
                                    )
                                )
                            )
                        )
                    ),
                    content = ArticleModel(
                        id = 123,
                        title = "Hello World",
                        summary = "Hmm d;lfksd ;fkhsa ldkhf;s aldkhfl sdjfaskdhalsdl asdak  sdhlas khdlask dlkasdas ldkhas/ ldkha /sldkh a/skd a/skda skhdas",
                        imageUrl = "https://i.ytimg.com/vi/0QaX4KMjeVQ/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLDEYHd9BOxp4Qnw0gdFKQe2fNf1xg",
                        publishedAt = "2021-08-03T20:40:02.000Z"
                    )
                )
            )
        )
    }

    override suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>> {
        return Resource.error("TO DO", null)
    }

    override suspend fun pressedLikeOnArticle(item: ContentWithLikesAndComments<ArticleModel>): Resource<Nothing> {
        item.likes.postValue(Resource.loading(item.likes.value?.data))
        item.likes.postValue(
            Resource.success(
                item.likes.value?.data?.plus(
                    UserModel(
                        id = "4",
                        avatarUrl = "https://lh3.googleusercontent.com/0xn6EsKc4lfdgFBt_1rA8uN6FgUUCrNO7cmTQny30x6wQhFrlTuZomwENpYsyMW00lytSuv6hLSHOs1voqpUautXcQ",
                        name = "Zach"
                    )
                )
            )
        )
        return Resource.success(null)
    }

    override suspend fun pressedLikeOnPost(item: ContentWithLikesAndComments<PostModel>): Resource<Nothing> {
        return Resource.error("TO DO", null)
    }

    override suspend fun pressedLikeOnComment(comment: MutableLiveData<Comment>): Resource<Nothing> {
        return Resource.error("TO DO", null)
    }

    override suspend fun sendMessage(
        message: String,
        id: Long,
        _class: Class<*>
    ): Resource<Nothing> {
        return Resource.error("TO DO", null)
    }

    override fun getCurrentUser(): UserModel = firebaseRepository.getCurrentUser()!!

}

