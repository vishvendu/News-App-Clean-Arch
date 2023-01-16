package com.vishvendu.cleanarch.news_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityLanguageListBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.CountryListFragment
import com.vishvendu.cleanarch.news_app.ui.fragment.LanguageListFragment

class LanguageListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLanguageListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        // fragment KTX replace this code
        val languageListFragment = LanguageListFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, languageListFragment).commit()
    }
}