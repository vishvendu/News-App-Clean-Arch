package com.vishvendu.cleanarch.news_app.data.repository

import android.annotation.SuppressLint
import androidx.room.withTransaction
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.local.db.NewsDataBase
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.utils.NetworkManager
import com.vishvendu.cleanarch.news_app.utils.Resource
import com.vishvendu.cleanarch.news_app.utils.networkBoundResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val db: NewsDataBase,
    private val networkManager: NetworkManager
) {

    private val dao = db.topHeadlineDAO()

    fun getTopHeadlines(country: String) = networkBoundResource(
        query = {
            dao.getAllTopHeadlineFlow()
        },
        fetch = {
            val response = networkService.getTopHeadlines(country)
            response.topHeadlineArticles
        },
        saveFetchResult = { topHeadlineResponse ->
            db.withTransaction {
                dao.deleteAllTopHeadline()
                dao.insert(topHeadlineResponse)
            }
        },
        shouldFetch = {
            networkManager.checkForInternet()
        }

    )

    // added coroutine functionality here work manager does not work with flow API as of now
    suspend fun updateHeadlineInDB(country: String): Resource<List<TopHeadlineArticle>> {
        var response: List<TopHeadlineArticle> = emptyList()
        withContext(Dispatchers.IO) {
            if (networkManager.checkForInternet()) {
                response = networkService.getTopHeadlines(country).topHeadlineArticles
                db.withTransaction {
                    dao.deleteAllTopHeadline()
                    dao.insert(response)
                }
            } else {
                response = dao.getAllTopHeadline()
            }
        }
        return Resource.success(response)
    }

}