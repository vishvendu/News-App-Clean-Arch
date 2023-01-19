package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.newssources.Source
import com.vishvendu.cleanarch.news_app.domain.repository.NewsSourcesRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class NewsSourcesViewModel(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<Source>>() {

    init {
        fetchSource()
    }

    private fun fetchSource() {
        viewModelScope.launch(dispatcherProvider.main){
            newsSourcesRepository.getNewsSources()
                .flowOn(dispatcherProvider.io)
                .catch {e ->
                    error(e.toString())
                }
                .collect {
                    success(it)
                }
        }
    }
}