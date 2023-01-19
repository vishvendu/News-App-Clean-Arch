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
import com.vishvendu.cleanarch.news_app.databinding.FragmentNewsInLanguageBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.newsinlanguage.Article
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.activity.NewsInLanguageActivity.Companion.EXTRA_LANGUAGE_ID
import com.vishvendu.cleanarch.news_app.ui.adapter.NewsInLanguageAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.NewsInLanguageViewModel
import com.vishvendu.cleanarch.news_app.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsInLanguageFragment : Fragment() {

    companion object {
        fun newInstance() : NewsInLanguageFragment {
            return NewsInLanguageFragment()
        }
    }

    @Inject
    lateinit var adapter: NewsInLanguageAdapter

    @Inject
    lateinit var newsInLanguageViewModel : NewsInLanguageViewModel

    private var _binding : FragmentNewsInLanguageBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentNewsInLanguageBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI()
        getIntentExtra()
        setupObserver()
        return view
    }

    private fun getIntentExtra(){
        var bundle :Bundle ?=activity?.intent?.extras
        var message = bundle!!.getSerializable(EXTRA_LANGUAGE_ID) as? HashMap<Int, String>
        if (message != null) {
            newsInLanguageViewModel.fetchNewsInTheLanguage(message)
        }
       /* message?.forEach {
                (key, value) ->  newsInLanguageViewModel.fetchNewsInTheLanguage(value)
        }*/


       // message?.let { newsInLanguageViewModel.fetchNewsInTheLanguage(it) }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsInLanguageViewModel.data.collect{
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