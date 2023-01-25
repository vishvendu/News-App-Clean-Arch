package com.vishvendu.cleanarch.news_app.data.api



import com.vishvendu.cleanarch.news_app.data.model.explore.ExploreNewsResponse
import com.vishvendu.cleanarch.news_app.data.model.newsforcountry.NewsForCountryResponse
import com.vishvendu.cleanarch.news_app.data.model.newsinlanguage.NewsInLanguageResponse
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.NewsSourceDetailsReponse
import com.vishvendu.cleanarch.news_app.data.model.newssources.NewsSources
import com.vishvendu.cleanarch.news_app.data.model.searchnews.SearchNewsResponse
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlinesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {


    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country : String) : TopHeadlinesResponse


    @GET("top-headlines/sources")
    suspend fun getNewsSources() : NewsSources


    @GET("top-headlines")
    suspend fun getNewsSourcesDetails(@Query("sources") source : String) : NewsSourceDetailsReponse


    @GET("top-headlines")
    suspend fun getNewsForCountry(@Query("country") country : String) : NewsForCountryResponse


    @GET("top-headlines")
    suspend fun getNewsInLanguage(@Query("language") country: String?) : NewsInLanguageResponse


    @GET("everything")
    suspend fun getSearchNews(@Query("q") search : String) : SearchNewsResponse


    @GET("everything")
    suspend fun fetchFeed(@Query("q") sources: String?, @Query("page") page: Long, @Query("pageSize") pageSize: Int): ExploreNewsResponse
}