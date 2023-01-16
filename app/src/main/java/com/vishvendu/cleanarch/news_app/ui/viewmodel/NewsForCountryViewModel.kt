package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.newsforcountry.Article
import com.vishvendu.cleanarch.news_app.domain.repository.NewsForCountryRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsForCountryViewModel(private val newsForCountryRepository: NewsForCountryRepository, private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _newsList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    val newsList: StateFlow<Resource<List<Article>>> = _newsList

    fun fetchNewsForTheCountry(country : String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsForCountryRepository.getNewsForCountry(country)
                .catch { e ->
                    _newsList.value = Resource.error(e.toString())
                }
                .collect {
                    _newsList.value = Resource.success(it)
                }
        }
    }


}