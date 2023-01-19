package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.languagelist.LanguageItem
import com.vishvendu.cleanarch.news_app.domain.repository.LanguageListRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LanguageListViewModel(private val languageListRepository: LanguageListRepository, private val dispatcherProvider: DispatcherProvider) : BaseViewModel<List<LanguageItem>>()  {

    init {
        fetchLanguageListDetails()
    }

    private fun fetchLanguageListDetails() {
        viewModelScope.launch(dispatcherProvider.main) {
            languageListRepository.getLanguageList()
                .catch { e ->
                    error(e.toString())
                }
                .collect {
                   success(it)
                }
        }
    }
}