package com.vishvendu.cleanarch.news_app.data.model.topheadlines

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headline_table")
data class TopHeadlineArticle(
    @ColumnInfo(name = "title") @PrimaryKey val title: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "sourceName") var sourceName:String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?
)