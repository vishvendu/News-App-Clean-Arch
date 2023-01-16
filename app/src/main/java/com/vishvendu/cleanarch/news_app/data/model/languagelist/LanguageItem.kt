package com.vishvendu.cleanarch.news_app.data.model.languagelist


import com.google.gson.annotations.SerializedName

data class LanguageItem(
    @SerializedName("code")
    val code: String,
    @SerializedName("label")
    val label: String,
    var isSelected: Boolean
)