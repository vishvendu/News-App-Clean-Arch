package com.vishvendu.cleanarch.news_app.ui.viewmodel

import com.vishvendu.cleanarch.news_app.base.BaseViewModel
import com.vishvendu.cleanarch.news_app.data.model.searchnews.Article
import com.vishvendu.cleanarch.news_app.domain.repository.SearchNewsRepository

class SearchNewsViewModel(private val searchNewsRepository: SearchNewsRepository) : BaseViewModel<List<Article>>() {

    fun searchNews(query: String) = searchNewsRepository.getNewsSources(query)

    fun searchSuccess(list : List<Article>) {
        success(list)
    }

    fun searchFailure(e : Throwable) {
        error(e.toString())
    }

    //TODO add loading

}