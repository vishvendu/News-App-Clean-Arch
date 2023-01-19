package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import com.vishvendu.cleanarch.news_app.domain.repository.NewsSourceDetailsRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourceDetailsViewModel(private val repository: NewsSourceDetailsRepository, private val dispatcherProvider: DispatcherProvider) : BaseViewModel<List<Article>>() {

    fun fetchNewsSourceDetails(source : String) {
        viewModelScope.launch(dispatcherProvider.main) {
                repository.getNewsSourcesDetails(source)
                    .catch { e ->
                        error(e.toString())
                    }
                    .collect {
                        success(it)
                    }
            }
    }
}