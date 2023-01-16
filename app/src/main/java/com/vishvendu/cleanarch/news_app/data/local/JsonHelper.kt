package com.vishvendu.cleanarch.news_app.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryList
import com.vishvendu.cleanarch.news_app.data.model.languagelist.Language
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class JsonHelper @Inject constructor(@ApplicationContext private val context: Context, private val gson: Gson) {
    private inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    fun getCountries(): CountryList {
        val jsonStringFile = readJson("country/countries.json")
        val listCountryType = genericType<CountryList>() // created inline function
        return gson.fromJson(jsonStringFile, listCountryType)
    }

    fun getLanguage(): Language {
        val jsonStringFile = readJson("language/language.json")
        val listLanguageType =genericType<Language>()
        return gson.fromJson(jsonStringFile, listLanguageType)
    }

    private fun readJson(filename : String) : String{
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open(filename)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {

        }
        return jsonString
    }


}