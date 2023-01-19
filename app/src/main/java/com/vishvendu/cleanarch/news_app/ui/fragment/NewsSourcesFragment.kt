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
import com.vishvendu.cleanarch.news_app.databinding.FragmentNewsSourcesBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.newssources.Source
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.adapter.NewsSourceAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.NewsSourcesViewModel
import com.vishvendu.cleanarch.news_app.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsSourcesFragment : Fragment() {

    private var _binding : FragmentNewsSourcesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: NewsSourceAdapter

    @Inject
    lateinit var newsSourcesViewModel : NewsSourcesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        getDependency()
        super.onCreate(savedInstanceState)
    }

    fun getInstance() : NewsSourcesFragment {
        return NewsSourcesFragment()
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
        _binding =  FragmentNewsSourcesBinding.inflate(inflater ,container,false)
        val view = binding.root
        setupUI()
        setupObserver()
        return view
    }

    private fun setupObserver() {
       lifecycleScope.launch {
           repeatOnLifecycle(Lifecycle.State.STARTED){
               newsSourcesViewModel.data.collect{
                   when(it.status){
                       Status.SUCCESS ->{
                           binding.progressBar.visibility = View.GONE
                           it.data?.let { newsList -> renderList(newsList) }
                           binding.newsSourcesRecyclerView.visibility = View.VISIBLE
                       }
                       Status.LOADING -> {
                           binding.progressBar.visibility = View.VISIBLE
                           binding.newsSourcesRecyclerView.visibility = View.GONE
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

    private fun renderList(articleList: List<Source>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
       val recyclerView = _binding?.newsSourcesRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,0
            )
        )
        recyclerView?.adapter = adapter
    }


}