package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.searchnews.Article
import com.vishvendu.cleanarch.news_app.domain.repository.SearchNewsRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchNewsViewModel(private val searchNewsRepository: SearchNewsRepository) : ViewModel() {

    private val _searchNewsList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    val searchNewsList: StateFlow<Resource<List<Article>>> = _searchNewsList

    /*fun searchNewsWithStateFlow(query : String) : Flow<Resource<List<Article>>> {
        return flow {
            searchNewsRepository.getNewsSources(query)
                .catch {e ->
                    _searchNewsList.value = Resource.error(e.toString())
                }
                .collect {
                    emit(Resource.success(it))
                    _searchNewsList.value = Resource.success(it)
                }
        }

    }*/

    fun searchNews(query: String) = searchNewsRepository.getNewsSources(query)

    fun success(list : List<Article>) {
        _searchNewsList.value = Resource.success(list)
    }

    fun failure(e : Throwable) {
        _searchNewsList.value = Resource.error(e.toString())
    }

    //TODO add loading

}