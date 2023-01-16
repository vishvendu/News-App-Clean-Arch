package com.vishvendu.cleanarch.news_app.data.repository

import android.content.Context
import com.vishvendu.cleanarch.news_app.data.api.NetworkService
import com.vishvendu.cleanarch.news_app.data.local.JsonHelper
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryList
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryListItem
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import com.vishvendu.cleanarch.news_app.utils.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// avoid writing android classes in the repo
class CountryListRepository @Inject constructor(private val jsonHelper: JsonHelper) {

    fun getCountryList(): User {
        return flow {
            emit(jsonHelper.getCountries())
        }.map {
            it
        }
    }
}

