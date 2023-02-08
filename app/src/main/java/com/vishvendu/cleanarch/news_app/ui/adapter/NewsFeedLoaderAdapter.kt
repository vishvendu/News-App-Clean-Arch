package com.vishvendu.cleanarch.news_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vishvendu.cleanarch.news_app.R

class NewsFeedLoaderAdapter : LoadStateAdapter<NewsFeedLoaderAdapter.NewsFeedLoaderViewHolder>() {

    override fun onBindViewHolder(holder: NewsFeedLoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NewsFeedLoaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false)
        return NewsFeedLoaderViewHolder(view)
    }

    class NewsFeedLoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.loaderProgressBar)
        val error: TextView = itemView.findViewById(R.id.loaderError)
        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            error.isVisible = loadState is LoadState.Error
        }
    }
}
