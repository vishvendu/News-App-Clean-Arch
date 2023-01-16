package com.vishvendu.cleanarch.news_app.domain.repository

import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.model.newssources.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository  @Inject constructor(private val networkService: NetworkService) {

    fun getNewsSources() : Flow<List<Source>>{
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }
}