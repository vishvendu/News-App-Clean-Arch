package com.vishvendu.cleanarch.news_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.domain.usecase.FetchTopHeadingUseCase
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.launch

class TopHeadlinesViewModel (private val fetchTopHeadingUseCase: FetchTopHeadingUseCase ) : ViewModel(){

    /*private val _TopHeadline_ArticleList = MutableStateFlow<Resource<List<TopHeadlineArticle>>>(Resource.loading())
    val topHeadlineArticleList: StateFlow<Resource<List<TopHeadlineArticle>>> = _TopHeadline_ArticleList*/


    private val _TopHeadline_ArticleList_LiveData = MutableLiveData<Resource<List<TopHeadlineArticle>>>()
    val topHeadlineArticleList_livedata: LiveData<Resource<List<TopHeadlineArticle>>> = _TopHeadline_ArticleList_LiveData

    init {
        //fetchNews()
        fetchNewsWithCoroutine()
    }

   // fetch news without flow API
   /* private fun fetchNews() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(COUNTRY)
                .catch { e ->
                    _TopHeadline_ArticleList.value = Resource.error(e.toString())
                }
                .collect {
                    _TopHeadline_ArticleList.value = Resource.success(it.data)
                }
        }
    }*/

    private fun fetchNewsWithCoroutine() {
        viewModelScope.launch {
            // TODO add try catch block to handle all kind of errors
           val response = fetchTopHeadingUseCase.invoke()
            _TopHeadline_ArticleList_LiveData.value = response
        }
    }
}