package com.vishvendu.cleanarch.news_app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.databinding.FragmentNewsFeedDetailsBinding
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.paging.NewsFeedAdapter
import com.vishvendu.cleanarch.news_app.ui.activity.NewsFeedDetailsActivity
import com.vishvendu.cleanarch.news_app.ui.activity.NewsForCountryActivity
import com.vishvendu.cleanarch.news_app.ui.adapter.NewsFeedLoaderAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsFeedDetailsFragment : Fragment() {

    private var _binding : FragmentNewsFeedDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: NewsFeedAdapter

    @Inject
    lateinit var newsFeedViewModel: NewsFeedViewModel

    companion object {
        fun newInstance(): NewsFeedDetailsFragment{
            return NewsFeedDetailsFragment()
        }
    }

    private fun getIntentExtra(){
        var bundle :Bundle ?=activity?.intent?.extras
        var message = bundle!!.getString(NewsFeedDetailsActivity.EXTRA_NEWS_FEED)
       // message?.let { newsFeedViewModel.newsFeed(message) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsFeedDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        setupUI()
        getIntentExtra()
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
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsFeedViewModel.newsFeed.collect{
                    adapter.submitData(it)
                    _binding?.progressBar?.visibility = View.GONE
                }
            }
        }
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
        recyclerView.setHasFixedSize(true)

        recyclerView?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsFeedLoaderAdapter(),
            footer = NewsFeedLoaderAdapter()
        )

        adapter.addLoadStateListener{loadState ->
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                if (errorState.error.localizedMessage == "HTTP 426") {
                    recyclerView?.adapter = adapter.withLoadStateFooter(
                        footer = NewsFeedLoaderAdapter()
                    )
                }
            }
        }
    }


}