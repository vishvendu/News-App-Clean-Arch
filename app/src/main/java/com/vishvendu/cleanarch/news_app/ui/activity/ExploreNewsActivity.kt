package com.vishvendu.cleanarch.news_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityExploreNewsBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.CountryListFragment
import com.vishvendu.cleanarch.news_app.ui.fragment.ExploreNewsFragment

class ExploreNewsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExploreNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val countryListFragment = ExploreNewsFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.exploreNewsContainer, countryListFragment).commit()
    }
}