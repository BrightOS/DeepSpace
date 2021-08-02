package ru.myitschool.nasa_bootcamp.data.repository

import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    firebaseRepository: FirebaseRepository,
    newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>> {
        return Resource.success(
            listOf(
                ContentWithLikesAndComments(
                    content = ArticleModel(
                        id = 123,
                        title = "Hello"
                    ),
                    comments = listOf(),
                    likes = 3
                )
            )
        )
    }

    override suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>> {
        return Resource.error("TO DO", null)
    }

}