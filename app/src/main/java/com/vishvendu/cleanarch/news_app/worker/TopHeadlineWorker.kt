package com.vishvendu.cleanarch.news_app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.utils.AppConstant.COUNTRY
import com.vishvendu.cleanarch.news_app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopHeadlineWorker(private val context: Context, param: WorkerParameters) :
    CoroutineWorker(context, param) {
    override suspend fun doWork(): Result {
        val repository = (context as MyNewsApplication).applicationComponent.getTopHeadlineRepository()
        return try {
            repository.updateHeadlineInDB(COUNTRY)
            Result.success()
        }catch ( e: Exception){
            Result.failure()
        }

        /*repository.getTopHeadlines(COUNTRY).catch { e ->
            println("error $e")
        }.collect {
            println("result $it")
        }*/

      //  return Result.success()
    }
}