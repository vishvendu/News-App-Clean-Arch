package com.vishvendu.cleanarch.news_app.di.component

import android.content.Context
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.local.JsonHelper
import com.vishvendu.cleanarch.news_app.data.local.db.NewsDataBase
import com.vishvendu.cleanarch.news_app.data.repository.NewsSourcesRepository
import com.vishvendu.cleanarch.news_app.data.repository.TopHeadlineRepository
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import com.vishvendu.cleanarch.news_app.di.NetworkApiKey
import com.vishvendu.cleanarch.news_app.di.NetworkBASEURL
import com.vishvendu.cleanarch.news_app.di.module.ApplicationModule
import com.vishvendu.cleanarch.news_app.utils.DefaultDispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.NetworkManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyNewsApplication)

    @ApplicationContext
    fun getContext(): Context

    @NetworkApiKey
    fun getNetworkAPIKey() : String

    @NetworkBASEURL
    fun getNetworkBASEURL() : String

    fun getNetworkService(): NetworkService

    fun getNetworkManager(): NetworkManager

    fun getCountryJsonHelper(): JsonHelper

    fun getGSON(): Gson

    fun getRoomDataBase() : NewsDataBase

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getNewsSourcesRepository(): NewsSourcesRepository

    fun getDispatcherProvider(): DefaultDispatcherProvider
}