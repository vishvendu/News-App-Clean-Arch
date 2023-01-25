package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.model.explore.Article
import com.vishvendu.cleanarch.news_app.domain.repository.NewsFeedRepository
import com.vishvendu.cleanarch.news_app.paging.NewsFeedPagingSource

class NewsFeedViewModel(private val newsFeedRepository: NewsFeedRepository,private val newsNetworkService: NetworkService) : ViewModel() {

    val newsFeed = newsFeedRepository.getNewsFeed("climate").cachedIn(viewModelScope)
   /* val newsFeed = Pager(PagingConfig(pageSize = 10,
           enablePlaceholders = false,
       initialLoadSize = 10)) {
        NewsFeedPagingSource(newsNetworkService,"climate")
    }.flow.cachedIn(viewModelScope)*/


}