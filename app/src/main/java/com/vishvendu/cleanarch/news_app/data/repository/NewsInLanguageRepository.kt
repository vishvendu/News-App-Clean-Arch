package com.vishvendu.cleanarch.news_app.data.repository

import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.model.newsinlanguage.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsInLanguageRepository @Inject constructor(private val networkService : NetworkService) {

    fun getNewsInLanguage(country: String?) : Flow<List<Article>> {
        println("NewsInLanguageRepository country list  $country")
        return flow {
            emit(networkService.getNewsInLanguage(country))
        }.map {
            it.articles
        }
    }


}