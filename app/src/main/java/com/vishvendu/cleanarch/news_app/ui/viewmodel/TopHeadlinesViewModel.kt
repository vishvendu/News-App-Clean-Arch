package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.data.repository.TopHeadlineRepository
import com.vishvendu.cleanarch.news_app.utils.AppConstant.COUNTRY
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopHeadlinesViewModel (private val topHeadlineRepository: TopHeadlineRepository) : ViewModel(){

    private val _TopHeadline_ArticleList = MutableStateFlow<Resource<List<TopHeadlineArticle>>>(Resource.loading())
    val topHeadlineArticleList: StateFlow<Resource<List<TopHeadlineArticle>>> = _TopHeadline_ArticleList


    private val _TopHeadline_ArticleList_LiveData = MutableLiveData<Resource<List<TopHeadlineArticle>>>()
    val topHeadlineArticleList_livedata: LiveData<Resource<List<TopHeadlineArticle>>> = _TopHeadline_ArticleList_LiveData

    init {
        //fetchNews()
        fetchNewsWithCoroutine()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(COUNTRY)
                .catch { e ->
                    _TopHeadline_ArticleList.value = Resource.error(e.toString())
                }
                .collect {
                    _TopHeadline_ArticleList.value = Resource.success(it.data)
                }
        }
    }

    private fun fetchNewsWithCoroutine() {
        viewModelScope.launch {
            // TODO add try catch block to handle all kind of errors
           val response = topHeadlineRepository.updateHeadlineInDB(COUNTRY)
            _TopHeadline_ArticleList_LiveData.value = response
        }
    }
}