package ru.myitschool.deepspace.data.model

/*
 * @author Yana Glad
 */
data class ArticleModel(
    val id: Long,
    val title: String,
    val url: String = "",
    val imageUrl: String = "",
    val newsSite: String = "",
    val summary: String = "",
    val publishedAt: String = "",
    val updatedAt: String = ""
)
