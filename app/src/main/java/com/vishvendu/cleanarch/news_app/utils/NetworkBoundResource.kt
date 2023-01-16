package com.vishvendu.cleanarch.news_app.utils

import kotlinx.coroutines.flow.*

fun <ResultType, RequestType> networkBoundResource(
    query : () -> Flow<ResultType>,
    fetch : suspend () -> RequestType,
    saveFetchResult : suspend (RequestType) -> Unit,
    shouldFetch : () -> Boolean
) = flow {this

    val flow =  if(shouldFetch()){
        try {
            saveFetchResult(fetch())
            query().map{Resource.success(it) }
        }catch (throwable : Throwable){
            query().map{Resource.error(" ${throwable.message}")}
        }
    }
    else{
        query().map{Resource.success(it) }
    }

    emitAll(flow)
}