package com.vishvendu.cleanarch.news_app.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityExploreNewsBinding
import com.vishvendu.cleanarch.news_app.databinding.ActivityNewsFeedDetailsBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.ExploreNewsFragment
import com.vishvendu.cleanarch.news_app.ui.fragment.NewsFeedDetailsFragment

class NewsFeedDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS_FEED = "news_feed"

        fun getStartIntent(context: Context, news : String) : Intent {
            return Intent(context, NewsFeedDetailsActivity::class.java)
                .apply {
                    putExtra(EXTRA_NEWS_FEED,news)
                }
        }
    }

    private lateinit var binding : ActivityNewsFeedDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFeedDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val countryListFragment = NewsFeedDetailsFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.exploreNewsFeedDetailContainer, countryListFragment).commit()
    }
}
