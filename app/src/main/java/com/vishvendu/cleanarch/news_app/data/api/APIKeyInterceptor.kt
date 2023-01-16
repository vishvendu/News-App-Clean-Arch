package com.vishvendu.cleanarch.news_app.data.api

import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("X-Api-Key", NewsNetworking.apiKey)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}