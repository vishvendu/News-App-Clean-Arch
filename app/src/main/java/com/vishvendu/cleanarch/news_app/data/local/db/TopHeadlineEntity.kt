package com.vishvendu.cleanarch.news_app.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineSource

@Entity(tableName = "top_headline_table")
data class TopHeadlineEntity(
    @PrimaryKey(autoGenerate = false) val title: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val topHeadlineSource: TopHeadlineSource,
    val url: String,
    val urlToImage: String
)