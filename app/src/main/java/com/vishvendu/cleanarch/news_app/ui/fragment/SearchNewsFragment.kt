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
import com.vishvendu.cleanarch.news_app.databinding.FragmentSearchNewsBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.newssources.Source
import com.vishvendu.cleanarch.news_app.data.model.searchnews.Article
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.adapter.SearchNewsAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.SearchNewsViewModel
import com.vishvendu.cleanarch.news_app.utils.Resource
import com.vishvendu.cleanarch.news_app.utils.Status
import com.vishvendu.cleanarch.news_app.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchNewsFragment : Fragment() {

    companion object {
        fun newInstance(): SearchNewsFragment {
            return SearchNewsFragment()
        }
    }

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: SearchNewsAdapter

    @Inject
    lateinit var searchNewsViewModel: SearchNewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        getDependency()
        super.onCreate(savedInstanceState)
    }

    private fun getDependency() {
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
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupSearchFlow()
        setupUI()
        setupObserver()
        return view
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchNewsViewModel.data.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
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



    private fun setupSearchFlow() {
        lifecycleScope.launch {
            // TODO add function in view model and call
            binding.searchView.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    // add loading
                    searchNewsViewModel.searchNews(query)
                        .catch {
                            emitAll(flowOf((emptyList())))
                        }
                }
                .flowOn(Dispatchers.IO).catch {e ->
                    searchNewsViewModel.searchFailure(e)
                }
                .collect {
                    searchNewsViewModel.searchSuccess(it)
                }
        }
    }

   /* private fun setupObserverForCoroutine() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchNewsViewModel.searchNewsList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
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
    }*/

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        val recyclerView = _binding?.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, 0
            )
        )
        recyclerView?.adapter = adapter
    }

}

