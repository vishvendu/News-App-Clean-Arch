package com.vishvendu.cleanarch.news_app.domain.repository

import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsSourceDetailsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsSourcesDetails(source: String) : Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsSourcesDetails(source))
        }.map {
            it.articles
        }
    }
}