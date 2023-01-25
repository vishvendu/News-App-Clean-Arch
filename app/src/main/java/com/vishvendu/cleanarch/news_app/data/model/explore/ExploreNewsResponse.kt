package com.vishvendu.cleanarch.news_app.data.model.explore


import com.google.gson.annotations.SerializedName

open class ExploreNewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)