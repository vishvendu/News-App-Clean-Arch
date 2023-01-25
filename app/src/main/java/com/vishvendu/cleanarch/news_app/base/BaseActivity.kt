package com.vishvendu.cleanarch.news_app.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding,VM : ViewModel>: AppCompatActivity() {

    lateinit var viewModels: VM
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        viewModels = getViewModel()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater) : VB

    companion object {
        fun initFragment(fragment: Fragment, layoutID : Int, fragmentManager: FragmentManager){
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(layoutID,fragment).commit()
        }
    }

    abstract fun getViewModel():VM

    abstract fun initObserver()

    abstract fun injectDependencies()

}