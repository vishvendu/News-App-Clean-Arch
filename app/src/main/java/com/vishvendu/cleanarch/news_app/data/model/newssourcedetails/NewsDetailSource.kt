package com.vishvendu.cleanarch.news_app.data.model.newssourcedetails


import com.google.gson.annotations.SerializedName

data class NewsDetailSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)