package com.vishvendu.cleanarch.news_app.di.component

import com.vishvendu.cleanarch.news_app.di.FragmentScope
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.fragment.*
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: CountryListFragment)
    fun inject(fragment: TopHeadlinesFragment)
    fun inject(fragment: NewsSourcesFragment)
    fun inject(fragment: NewsForCountryFragment)
    fun inject(fragment: LanguageListFragment)
    fun inject(fragment: NewsInLanguageFragment)
    fun inject(fragment: SearchNewsFragment)
}