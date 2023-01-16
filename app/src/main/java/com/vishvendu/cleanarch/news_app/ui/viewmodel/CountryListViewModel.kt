package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryListItem
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import com.vishvendu.cleanarch.news_app.domain.repository.CountryListRepository
import com.vishvendu.cleanarch.news_app.utils.DispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryListViewModel(private val countryListRepository: CountryListRepository, private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    //TODO make baseview model
    private val _countryList = MutableStateFlow<Resource<List<CountryListItem>>>(Resource.loading())

    val countryList: StateFlow<Resource<List<CountryListItem>>> = _countryList

    init {
        fetchCountryListDetails()
    }
// add flowOn wherever we are reading from the file
    private fun fetchCountryListDetails() {
        viewModelScope.launch(dispatcherProvider.main) {
            countryListRepository.getCountryList().flowOn(Dispatchers.IO)
                .catch { e ->
                    _countryList.value = Resource.error(e.toString())
                }
                .collect {
                    _countryList.value = Resource.success(it)
                }
        }
    }
}