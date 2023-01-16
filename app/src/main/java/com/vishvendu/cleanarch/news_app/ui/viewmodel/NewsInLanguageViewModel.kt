package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.newsinlanguage.Article
import com.vishvendu.cleanarch.news_app.data.repository.NewsInLanguageRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsInLanguageViewModel (private val newsInLanguageRepository: NewsInLanguageRepository, private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _newsList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    val newsList: StateFlow<Resource<List<Article>>> = _newsList

    fun fetchNewsInTheLanguage(country: HashMap<Int,String>) {
        viewModelScope.launch(dispatcherProvider.main) {
            _newsList.value = Resource.loading()
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
                    _newsList.value = Resource.error(e.toString())
                }
                .collect {
                    _newsList.value = Resource.success(it)
                }

        }
    }


}