package com.vishvendu.cleanarch.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishvendu.cleanarch.news_app.databinding.FragmentNewsForCountryBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.newsforcountry.Article
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.activity.NewsForCountryActivity
import com.vishvendu.cleanarch.news_app.ui.adapter.NewsForCountryAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.NewsForCountryViewModel
import com.vishvendu.cleanarch.news_app.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsForCountryFragment : Fragment() {

    companion object {
        fun newInstance() : NewsForCountryFragment {
            return NewsForCountryFragment()
        }
    }

    @Inject
    lateinit var adapter: NewsForCountryAdapter

    @Inject
    lateinit var newsForCountryViewModel : NewsForCountryViewModel

    private var _binding : FragmentNewsForCountryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun getIntentExtra(){
        var bundle :Bundle ?=activity?.intent?.extras
        var message = bundle!!.getString(NewsForCountryActivity.EXTRA_COUNTRY_ID)
        message?.let { newsForCountryViewModel.fetchNewsForTheCountry(it) }
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
        _binding = FragmentNewsForCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI()
        getIntentExtra()
        setupObserver()
        return view
    }


    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsForCountryViewModel.newsList.collect{
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
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG)
                                .show()
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

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView?.adapter = adapter
    }


}