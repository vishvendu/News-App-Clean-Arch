package com.vishvendu.cleanarch.news_app.domain.usecase

import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.domain.repository.TopHeadlineRepository
import com.vishvendu.cleanarch.news_app.utils.AppConstant
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTopHeadingUseCase @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) {
    suspend operator fun invoke() : Resource<List<TopHeadlineArticle>> = topHeadlineRepository.getTopHeadlinesWithCoroutines(AppConstant.COUNTRY)

}