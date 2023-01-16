package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import com.vishvendu.cleanarch.news_app.data.repository.NewsSourceDetailsRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourceDetailsViewModel(private val repository: NewsSourceDetailsRepository, private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _articleList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    val articleList: StateFlow<Resource<List<Article>>> = _articleList

    fun fetchNewsSourceDetails(source : String) {
        viewModelScope.launch(dispatcherProvider.main) {
                repository.getNewsSourcesDetails(source)
                    .catch { e ->
                        _articleList.value = Resource.error(e.toString())
                    }
                    .collect {
                        _articleList.value = Resource.success(it)
                    }
            }
    }
}