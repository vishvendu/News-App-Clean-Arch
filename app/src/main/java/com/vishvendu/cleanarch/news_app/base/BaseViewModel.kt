package com.vishvendu.cleanarch.news_app.base

import androidx.lifecycle.ViewModel
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


abstract class BaseViewModel<T>() : ViewModel() {

    private val _data = MutableStateFlow<Resource<T>>(Resource.loading())
    val data: StateFlow<Resource<T>> = _data


   fun success(data : T){
       _data.value = Resource.success(data)
    }

    fun <T> error(data : T){
        _data.value = Resource.error(data.toString())
    }

    fun loading(){
        _data.value = Resource.loading()
    }

}