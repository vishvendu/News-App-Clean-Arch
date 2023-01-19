package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.newsinlanguage.Article
import com.vishvendu.cleanarch.news_app.domain.repository.NewsInLanguageRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsInLanguageViewModel (private val newsInLanguageRepository: NewsInLanguageRepository, private val dispatcherProvider: DispatcherProvider) : BaseViewModel<List<Article>>() {

    fun fetchNewsInTheLanguage(country: HashMap<Int,String>) {
        viewModelScope.launch(dispatcherProvider.main) {
            loading()
            newsInLanguageRepository.getNewsInLanguage(country[0]).catch {
                emitAll(flowOf(emptyList()))
            }
                .zip( newsInLanguageRepository.getNewsInLanguage(country[1]).catch {
                    emitAll(flowOf(emptyList()))
                }) { getNewsFromCountry, getNewsFromMoreCountry ->
                    val newsFromApi = mutableListOf<Article>()
                    newsFromApi.addAll(getNewsFromCountry)
                    newsFromApi.addAll(getNewsFromMoreCountry)
                    newsFromApi.shuffle()
                    return@zip newsFromApi
                }
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    error(e.toString())
                }
                .collect {
                    success(it)
                }

        }
    }


}