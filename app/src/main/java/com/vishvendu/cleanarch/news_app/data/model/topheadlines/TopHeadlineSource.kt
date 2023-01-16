package com.vishvendu.cleanarch.news_app.data.model.topheadlines


import com.google.gson.annotations.SerializedName

data class TopHeadlineSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)