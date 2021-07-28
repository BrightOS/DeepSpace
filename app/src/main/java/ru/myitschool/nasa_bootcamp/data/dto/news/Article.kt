package ru.myitschool.nasa_bootcamp.data.dto.news

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel

class Article(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("imageUrl") val imageUrl: String,
    @field:SerializedName("newsSite") val newsSite: String,
    @field:SerializedName("summary") val summary: String,
    @field:SerializedName("publishedAt") val publishedAt: String,
    @field:SerializedName("updatedAt") val updatedAt: String
) {
    fun createArticleModel() : ArticleModel{
        return ArticleModel(id, title, url, imageUrl, newsSite, summary, publishedAt, updatedAt)
    }
}

//
//{
//    "id": 10146,
//    "title": "NASA Celebrates National Intern Day 2021",
//    "url": "http://www.nasa.gov/press-release/nasa-celebrates-national-intern-day-2021",
//    "imageUrl": "https://www.nasa.gov/sites/default/files/thumbnails/image/glenn_internship_af_1.jpg?itok=74-B2i7N",
//    "newsSite": "NASA",
//    "summary": "NASA will host a variety of events and interactive opportunities celebrating the agency’s interns and their contributions to its missions in recognition of National Intern Day, held this year on Thursday, July 29.",
//    "publishedAt": "2021-07-28T14:42:00.000Z",
//    "updatedAt": "2021-07-28T14:42:20.700Z",
//    "featured": false,
//    "launches": [],
//    "events": []
//},