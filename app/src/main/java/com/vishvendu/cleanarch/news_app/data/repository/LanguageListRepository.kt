package com.vishvendu.cleanarch.news_app.data.repository

import android.content.Context
import com.vishvendu.cleanarch.news_app.data.local.JsonHelper
import com.vishvendu.cleanarch.news_app.data.model.languagelist.LanguageItem
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageListRepository  @Inject constructor(private val jsonHelper: JsonHelper, @ApplicationContext private val context: Context)   {

    fun getLanguageList(): Flow<List<LanguageItem>> {
        return flow {
            emit(jsonHelper.getLanguage())
        }.map {
            it
        }
    }
}