package com.vishvendu.cleanarch.news_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vishvendu.cleanarch.news_app.ui.activity.*
import com.vishvendu.cleanarch.news_app.databinding.FragmentMainBinding
import com.vishvendu.cleanarch.news_app.ui.activity.*

class MainFragment : Fragment() {

    companion object{
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
    private var _binding : FragmentMainBinding ? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI()
        return view
    }

    private fun setupUI() {
        binding.let {
            val topHeadline = it.topHeadlines
            val newsSources = it.newsSources
            val countries = it.country
            val language = it.languages
            val search = it.search

            topHeadline.setOnClickListener{
                val intent = Intent(activity, TopHeadlinesActivity::class.java)
                startActivity(intent)
            }

            newsSources.setOnClickListener{
                val intent = Intent(activity, NewsSourcesActivity::class.java)
                startActivity(intent)
            }

            countries.setOnClickListener{
                val intent = Intent(activity, CountryListActivity::class.java)
                startActivity(intent)
            }

            language.setOnClickListener{
                val intent = Intent(activity, LanguageListActivity::class.java)
                startActivity(intent)
            }

            search.setOnClickListener{
                val intent = Intent(activity, SearchNewsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}