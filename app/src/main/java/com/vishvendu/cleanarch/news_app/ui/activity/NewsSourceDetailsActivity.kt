package com.vishvendu.cleanarch.news_app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import com.vishvendu.cleanarch.news_app.data.repository.NewsSourceDetailsRepository
import com.vishvendu.cleanarch.news_app.databinding.ActivityNewsSourceDetailsBinding
import com.vishvendu.cleanarch.news_app.di.component.DaggerActivityComponent
import com.vishvendu.cleanarch.news_app.di.module.ActivityModule
import com.vishvendu.cleanarch.news_app.ui.adapter.NewsSourceDetailsAdapter
import com.vishvendu.cleanarch.news_app.ui.base.ViewModelProviderFactory
import com.vishvendu.cleanarch.news_app.ui.viewmodel.NewsSourceDetailsViewModel
import com.vishvendu.cleanarch.news_app.utils.DefaultDispatcherProvider
import com.vishvendu.cleanarch.news_app.utils.Status
import com.vishvendu.cleanarch.newsapp.base.BaseActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsSourceDetailsActivity : BaseActivity<ActivityNewsSourceDetailsBinding, NewsSourceDetailsViewModel>() {

    companion object{
        const val EXTRA_NEWS_ID = "news_id"

        fun getStartIntent(context: Context, newsID : String) : Intent {
           return Intent(context, NewsSourceDetailsActivity::class.java)
                .apply {
                    putExtra(EXTRA_NEWS_ID,newsID)
                }
        }
    }

    @Inject
    lateinit var adapter: NewsSourceDetailsAdapter

    @Inject
    lateinit var dispatcherProvider: DefaultDispatcherProvider

    @Inject
    lateinit var newsSourceDetailsRepository : NewsSourceDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        initView()
        getIntentExtra()
        initObserver()
    }

    private fun initView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,0
            )
        )
        recyclerView.adapter = adapter
    }

    private fun getIntentExtra(){
        var bundle :Bundle ?=intent.extras
        var message = bundle!!.getString(EXTRA_NEWS_ID)
        message?.let { viewModels.fetchNewsSourceDetails(it) }
    }

     override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModels.articleList.collect{
                    when(it.status){
                        Status.SUCCESS ->{
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { newsList -> renderList(newsList) }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    override fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyNewsApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }



    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityNewsSourceDetailsBinding{
       return ActivityNewsSourceDetailsBinding.inflate(layoutInflater)
    }

    override fun getViewModel(): NewsSourceDetailsViewModel {
        return ViewModelProvider(this,
            ViewModelProviderFactory(NewsSourceDetailsViewModel::class) {
                NewsSourceDetailsViewModel(newsSourceDetailsRepository,dispatcherProvider)
            })[NewsSourceDetailsViewModel::class.java]
    }



}