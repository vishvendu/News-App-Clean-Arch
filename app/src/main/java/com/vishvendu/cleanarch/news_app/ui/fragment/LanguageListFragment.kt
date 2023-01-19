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
import com.vishvendu.cleanarch.news_app.databinding.FragmentLanguageListBinding
import com.vishvendu.cleanarch.news_app.MyNewsApplication
import com.vishvendu.cleanarch.news_app.data.model.languagelist.LanguageItem
import com.vishvendu.cleanarch.news_app.di.component.DaggerFragmentComponent
import com.vishvendu.cleanarch.news_app.di.module.FragmentModule
import com.vishvendu.cleanarch.news_app.ui.activity.NewsInLanguageActivity
import com.vishvendu.cleanarch.news_app.ui.adapter.LanguageListAdapter
import com.vishvendu.cleanarch.news_app.ui.viewmodel.LanguageListViewModel
import com.vishvendu.cleanarch.news_app.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageListFragment : Fragment() {

    companion object{
        fun newInstance(): LanguageListFragment {
            return LanguageListFragment()
        }
    }

    private var _binding : FragmentLanguageListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: LanguageListAdapter

    @Inject
    lateinit var languageListViewModel : LanguageListViewModel


    private var languageSelectedMap = HashMap<Int,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentLanguageListBinding.inflate(inflater ,container,false)
        val view = binding.root
        setupUI()
        setupObserver()
        return view
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                languageListViewModel.data.collect{
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

    private fun setupUI() {
        val recyclerView = _binding?.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,0
            )
        )
        recyclerView?.adapter = adapter
        adapter.itemClickListener = {map ->
            languageSelectedMap = map
        }

        binding.buttonLanguage.setOnClickListener{

            startActivity(NewsInLanguageActivity.getStartIntent(requireContext(),languageSelectedMap))

        }
    }

    private fun injectDependencies(){
        DaggerFragmentComponent
            .builder()
            .applicationComponent((requireContext().applicationContext as MyNewsApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }

    private fun renderList(articleList: List<LanguageItem>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }


}