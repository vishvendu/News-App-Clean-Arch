package com.vishvendu.cleanarch.news_app.di.component

import android.app.Activity
import android.content.Context
import com.vishvendu.cleanarch.news_app.di.ActivityContext
import com.vishvendu.cleanarch.news_app.di.ActivityScope
import com.vishvendu.cleanarch.news_app.di.ApplicationContext
import com.vishvendu.cleanarch.news_app.di.module.ActivityModule
import com.vishvendu.cleanarch.news_app.ui.activity.MainActivity
import com.vishvendu.cleanarch.news_app.ui.activity.NewsInLanguageActivity
import com.vishvendu.cleanarch.news_app.ui.activity.NewsSourceDetailsActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: NewsSourceDetailsActivity)
    fun inject(activity: NewsInLanguageActivity)
}