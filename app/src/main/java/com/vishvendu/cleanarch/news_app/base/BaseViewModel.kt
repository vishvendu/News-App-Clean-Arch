package com.vishvendu.cleanarch.newsapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>() : ViewModel() {

   /* private val _dataList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    private val dataList: Flow<Resource<List<Article>>> = _dataList*/

    protected val _state = MutableStateFlow<T>(initialState())

    val state: StateFlow<T>
        get() = _state

    abstract fun initialState(): T

    fun updateState(newState: T) {
        _state.value = newState
    }

}