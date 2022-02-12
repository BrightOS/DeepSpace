package ru.berserkers.deepspace.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.berserkers.deepspace.data.fb_general.BlogPagingSource
import ru.berserkers.deepspace.data.model.*
import ru.berserkers.deepspace.utils.Resource
import ru.berserkers.deepspace.utils.Status
import javax.inject.Inject

/*
 * @author Vladimir Abubakirov
 */
class SocialMediaRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val newsRepository: NewsRepository,
) : SocialMediaRepository {
    override suspend fun getNews(): Resource<List<LiveData<ContentWithLikesAndComments<ArticleModel>>>> {
        val newsList = mutableListOf<LiveData<ContentWithLikesAndComments<ArticleModel>>>()
        val news = newsRepository.getNews()
        if (news.status == Status.ERROR) {
            return Resource.error(news.message!!, null)
        }
        for (article in news.data.orEmpty()) {
            val data = MutableLiveData<ContentWithLikesAndComments<ArticleModel>>()
            val comments = firebaseRepository.getArticleModelComments(article.id)
            val likes = firebaseRepository.getArticleModelLikes(article.id)
            data.postValue(ContentWithLikesAndComments(article, likes, comments))
            firebaseRepository.articleModelEventListener(data, article.id)
            newsList.add(data)
        }
        return Resource.success(
            newsList
        )
    }

    override suspend fun getBlogPosts(): Resource<List<LiveData<ContentWithLikesAndComments<PostModel>>>> {
        val result = firebaseRepository.getAllPostsRawData()
        if (result.status == Status.ERROR) {
            return Resource.error(result.message.toString(), null)
        }
        return Resource.success(result.data)
    }

    override fun getBlogPagingSource() = BlogPagingSource(firebaseRepository)
    override suspend fun pressedLikeOnItem(
        item: ContentWithLikesAndComments<out Any>,
    ): Resource<Unit> {
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
                    (item.content as PostModel).id.toInt()
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
        comment: Comment,
    ): Resource<Unit> {
        try {
            if (comment::class.java != SubComment::class.java) {
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
                        (item.content as PostModel).id.toInt(),
                        comment.id
                    )
                    if (result.status == Status.ERROR) {
                        return Resource.error(result.message.toString(), null)
                    }
                }
            } else {
                if (item.content::class.java == ArticleModel::class.java) {
                    val result = firebaseRepository.pushLikeForSubComment(
                        "ArticleModel",
                        (item.content as ArticleModel).id.toInt(),
                        (comment as SubComment).parentComment.id,
                        comment.id
                    )
                    if (result.status == Status.ERROR) {
                        return Resource.error(result.message.toString(), null)
                    }
                } else {
                    val result = firebaseRepository.pushLikeForSubComment(
                        "UserPost",
                        (item.content as PostModel).id.toInt(),
                        (comment as SubComment).parentComment.id,
                        comment.id
                    )
                    if (result.status == Status.ERROR) {
                        return Resource.error(result.message.toString(), null)
                    }
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
        parentComment: Comment?,
    ): Resource<Unit> {
        if (parentComment == null) {
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
        } else {
            if (_class == ArticleModel::class.java) {
                val result = firebaseRepository.pushSubComment(
                    "ArticleModel",
                    id.toInt(),
                    parentComment.id,
                    message
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            } else {
                val result = firebaseRepository.pushSubComment(
                    "UserPost",
                    id.toInt(),
                    parentComment.id,
                    message
                )
                if (result.status == Status.ERROR) {
                    return Resource.error(result.message.toString(), null)
                }
            }
        }
        return Resource.success(null)
    }

    override suspend fun getUser(uid: String): UserModel? {
        return firebaseRepository.getUser(uid)
    }

    override suspend fun createPost(title: String, postItems: List<Any>): Resource<Unit> {
        if (title.length > 200)
            return Resource.error("Too long title", null)
        if (postItems.size > 200)
            return Resource.error("Too many data in post", null)
        if (postItems.isEmpty())
            return Resource.error("Post without aby data", null)
        val result = firebaseRepository.createPost(title, postItems)
        if (result.status == Status.ERROR) {
            return Resource.error(result.message.toString(), null)
        }
        return Resource.success(null)
    }

    override suspend fun deleteComment(
        comment: Comment,
        item: ContentWithLikesAndComments<out Any>,
    ): Resource<Unit> {
        val source =
            if (item.content is ArticleModel) "ArticleModel"
            else "UserPost"
        val postId = when (item.content) {
            is ArticleModel -> item.content.id
            is PostModel -> item.content.id
            else -> throw ClassCastException("Couldn't cast item to ArticleModel or PostModel")
        }
        return if (comment is SubComment) firebaseRepository.deleteSubComment(
            source = source,
            postId = postId.toInt(),
            subCommentId = comment.id,
            fatherCommentId = comment.parentComment.id
        ) else firebaseRepository.deleteComment(
            source = source,
            postId = postId.toInt(),
            commentId = comment.id
        )
    }
    override suspend fun getCurrentUser(): UserModel? = firebaseRepository.getCurrentUser()
}
