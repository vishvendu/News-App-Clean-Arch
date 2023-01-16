package com.vishvendu.cleanarch.news_app.data.model.newssources


import com.google.gson.annotations.SerializedName

data class NewsSources(
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("status")
    val status: String
)