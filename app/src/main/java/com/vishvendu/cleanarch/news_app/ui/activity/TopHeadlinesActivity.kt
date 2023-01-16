package com.vishvendu.cleanarch.news_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityTopHeadlineBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.TopHeadlinesFragment

class TopHeadlinesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTopHeadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val topHeadlinesFragment = TopHeadlinesFragment.newInstance()
        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.topHeadlineContainer, topHeadlinesFragment).commit()
    }
}