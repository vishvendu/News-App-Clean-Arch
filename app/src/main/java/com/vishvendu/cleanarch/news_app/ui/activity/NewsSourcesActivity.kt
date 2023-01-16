package com.vishvendu.cleanarch.news_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityNewsSourcesBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.NewsSourcesFragment

class NewsSourcesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewsSourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val fragment = NewsSourcesFragment().getInstance()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.newsSourceContainer,fragment).commit()
    }
}