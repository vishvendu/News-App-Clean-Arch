package com.vishvendu.cleanarch.news_app.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.api.APIKeyInterceptor
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.local.JsonHelper
import com.vishvendu.cleanarch.news_app.data.local.db.NewsDataBase
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import com.vishvendu.cleanarch.news_app.di.NetworkApiKey
import com.vishvendu.cleanarch.news_app.di.NetworkBASEURL
import com.vishvendu.cleanarch.news_app.utils.DefaultDispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyNewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(@NetworkBASEURL baseurl: String, gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryJsonHelper(): JsonHelper {
        return JsonHelper(application, provideGson())
    }

    @Provides
    @Singleton
    fun provideOkHttp(provideAPIKeyInterceptor : APIKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor (provideAPIKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIKeyInterceptor() : APIKeyInterceptor {
        return APIKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideNetworkManager() : NetworkManager {
        return NetworkManager(application)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRoomDataBase() : NewsDataBase = Room.databaseBuilder(application,NewsDataBase::class.java,"news_database").build()

    @Provides
    @NetworkApiKey
    fun provideApiKey(): String = "f3625c6afa4f486e9ad5d25293a6f4b2"

    @Provides
    @NetworkBASEURL
    fun provideBaseURL(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideDispatcher(): DefaultDispatcherProvider {
        return DefaultDispatcherProvider()
    }
}