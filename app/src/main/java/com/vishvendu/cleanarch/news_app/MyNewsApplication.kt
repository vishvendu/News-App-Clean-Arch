package com.vishvendu.cleanarch.news_app

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.vishvendu.cleanarch.news_app.worker.TopHeadlineWorker
import com.vishvendu.cleanarch.news_app.di.component.ApplicationComponent
import com.vishvendu.cleanarch.news_app.di.component.DaggerApplicationComponent
import com.vishvendu.cleanarch.news_app.di.module.ApplicationModule
import java.util.concurrent.TimeUnit

class MyNewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        getDependency()
        initWorker()
    }
    // TODO add this in some other class and inject this call using dagger
    private fun initWorker(){
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest.Builder(TopHeadlineWorker::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun getDependency() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}