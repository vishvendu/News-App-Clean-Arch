package com.vishvendu.cleanarch.news_app.data.model.newsinlanguage


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)