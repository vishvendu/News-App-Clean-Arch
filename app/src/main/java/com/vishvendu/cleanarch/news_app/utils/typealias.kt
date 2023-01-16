package com.vishvendu.cleanarch.news_app.utils

import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryListItem
import kotlinx.coroutines.flow.Flow

typealias ItemClickListener<T> = (data: T) -> Unit

typealias User = Flow<List<CountryListItem>>