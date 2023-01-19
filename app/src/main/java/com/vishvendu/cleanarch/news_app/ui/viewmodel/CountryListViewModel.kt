package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryListItem
import com.vishvendu.cleanarch.news_app.domain.repository.CountryListRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryListViewModel(private val countryListRepository: CountryListRepository, private val dispatcherProvider: DispatcherProvider) : BaseViewModel<List<CountryListItem>>() {

    init {
        fetchCountryListDetails()
    }
    // add flowOn wherever we are reading from the file
    private fun fetchCountryListDetails() {
        viewModelScope.launch(dispatcherProvider.main) {
            countryListRepository.getCountryList().flowOn(Dispatchers.IO)
                .catch { e ->
                    error(e.toString())
                }
                .collect {
                    success(it)
                }
        }
    }
}