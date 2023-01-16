package com.vishvendu.cleanarch.news_app.data.model.topheadlines


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top_headline_response_table")
data class TopHeadlinesResponse(
    @SerializedName("articles")
    @PrimaryKey val topHeadlineArticles: List<TopHeadlineArticle>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)