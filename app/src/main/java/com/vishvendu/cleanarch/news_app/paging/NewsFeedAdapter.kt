package com.vishvendu.cleanarch.news_app.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishvendu.cleanarch.news_app.data.model.explore.Article
import com.vishvendu.cleanarch.news_app.databinding.ItemNewsFeedBinding

class NewsFeedAdapter(private val context: Context) : PagingDataAdapter<Article, NewsFeedAdapter.NewsViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
      return NewsViewHolder(
            ItemNewsFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

companion object {
    private val COMPARATOR = object  : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           return  oldItem.title == newItem.title
        }

    }
}
    class NewsViewHolder(private val binding: ItemNewsFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?, context: Context) {
            binding.newsFeedTextViewTitle.text = article?.title
            binding.newsFeedTextViewSource.text = article?.source?.name
            Glide.with(binding.newsFeedImageViewBanner.context)
                .load(article?.urlToImage)
                .into(binding.newsFeedImageViewBanner)
        }
    }



}