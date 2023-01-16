package com.vishvendu.cleanarch.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishvendu.cleanarch.news_app.databinding.FragmentTopHeadlinesBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.adapter.TopHeadlinesAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.TopHeadlinesViewModel
import com.vishvendu.cleanarch.news_app.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopHeadlinesFragment : Fragment() {

    companion object {
        fun newInstance() : TopHeadlinesFragment {
            return TopHeadlinesFragment()
        }
    }

    private var _binding : FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: TopHeadlinesAdapter

    @Inject
    lateinit var topHeadlinesViewModel : TopHeadlinesViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI()
        setupObserver()
        return view
    }

    private fun injectDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((requireContext().applicationContext as MyNewsApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            /*repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlinesViewModel.topHeadlineArticleList.collect {
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
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }*/

            repeatOnLifecycle(Lifecycle.State.STARTED){
                topHeadlinesViewModel.topHeadlineArticleList_livedata.observe(viewLifecycleOwner)  {
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
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(topHeadlineArticleList: List<TopHeadlineArticle>) {
        adapter.addData(topHeadlineArticleList)
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