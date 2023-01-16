package com.vishvendu.cleanarch.news_app.di

import javax.inject.Qualifier

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class ApplicationContext

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class ActivityContext

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class NetworkApiKey

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class NetworkBASEURL
