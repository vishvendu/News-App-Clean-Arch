package com.vishvendu.cleanarch.news_app.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.paging.NewsFeedPagingSource
import javax.inject.Inject

class NewsFeedRepository @Inject constructor(private val newsNetworkService: NetworkService) {

    fun getNewsFeed(newsfeed: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { NewsFeedPagingSource(newsNetworkService,newsfeed) }
    ).flow
}