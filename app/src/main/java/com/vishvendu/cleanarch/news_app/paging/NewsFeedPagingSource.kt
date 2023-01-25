package com.vishvendu.cleanarch.news_app.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.api.NewsNetworking
import com.vishvendu.cleanarch.news_app.data.model.explore.Article
import javax.inject.Inject

class NewsFeedPagingSource @Inject constructor(private val networkService : NetworkService, private val newsFeed : String): PagingSource<Int, Article>(){

    /*override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 1
            val response = networkService.fetchFeed(newsFeed,1,position)
            return LoadResult.Page(
                data = response.articles,
                prevKey = if(position == 1) null else position -1,
                nextKey = if(position == response.totalResults) null else position +1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }*/


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = networkService.fetchFeed(newsFeed,1,currentLoadingPageKey)
            val responseData = mutableListOf<Article>()
            val data = response.articles
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}