package com.vishvendu.cleanarch.news_app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityNewsInLanguageBinding
import com.vishvendu.cleanarch.news_app.di.component.DaggerActivityComponent
import com.vishvendu.cleanarch.news_app.di.module.ActivityModule
import com.vishvendu.cleanarch.news_app.ui.fragment.NewsInLanguageFragment

class NewsInLanguageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LANGUAGE_ID = "language_id"

        fun getStartIntent(context: Context, country: HashMap<Int, String>) : Intent {
            return Intent(context, NewsInLanguageActivity::class.java)
                .apply {
                    putExtra(EXTRA_LANGUAGE_ID,country)
                }
        }

    }

    private lateinit var binding : ActivityNewsInLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsInLanguageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val fragment = NewsInLanguageFragment.newInstance()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment).commit()
    }


    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyNewsApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

}