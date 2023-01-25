package com.vishvendu.cleanarch.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.R
import com.vishvendu.cleanarch.news_app.databinding.FragmentExploreNewsBinding
import com.vishvendu.cleanarch.news_app.databinding.FragmentSearchNewsBinding
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.activity.NewsFeedDetailsActivity
import com.vishvendu.cleanarch.news_app.ui.activity.NewsSourceDetailsActivity

class ExploreNewsFragment : Fragment() {

    private lateinit var _binding : FragmentExploreNewsBinding

    companion object{
        fun newInstance(): ExploreNewsFragment {
            return ExploreNewsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun injectDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((requireContext().applicationContext as MyNewsApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreNewsBinding.inflate(inflater, container, false)
        val view = _binding.root
        setupUI()
        return view
    }

    private fun setupUI() {
        _binding.climate.setOnClickListener {
            startActivity(context?.let { it1 -> NewsFeedDetailsActivity.getStartIntent(it1,
                _binding.climate.text.toString()
            ) })
        }
    }
}