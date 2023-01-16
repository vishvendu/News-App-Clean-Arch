package com.vishvendu.cleanarch.news_app.data.model.newsforcountry


import com.google.gson.annotations.SerializedName

data class NewsForCountryResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)