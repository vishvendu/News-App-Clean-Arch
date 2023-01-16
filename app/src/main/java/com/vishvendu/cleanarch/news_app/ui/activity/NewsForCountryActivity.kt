package com.vishvendu.cleanarch.news_app.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.ActivityNewsForCountryBinding
import com.vishvendu.cleanarch.news_app.ui.fragment.NewsForCountryFragment

class NewsForCountryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COUNTRY_ID = "country_id"

        fun getStartIntent(context: Context, country : String) : Intent {
            return Intent(context, NewsForCountryActivity::class.java)
                .apply {
                    putExtra(EXTRA_COUNTRY_ID,country)
                }
        }
    }

    private lateinit var binding : ActivityNewsForCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsForCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        val fragment = NewsForCountryFragment.newInstance()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment).commit()
    }
}