package com.vishvendu.cleanarch.news_app.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.databinding.TopHeadlineItemLayoutBinding


class TopHeadlinesAdapter( private val topHeadlineArticleList: ArrayList<TopHeadlineArticle>) :
    RecyclerView.Adapter<TopHeadlinesAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topHeadlineArticle: TopHeadlineArticle) {
            binding.textViewTitle.text = topHeadlineArticle.title
            binding.textViewDescription.text = topHeadlineArticle.description
            binding.textViewSource.text = topHeadlineArticle.content
            Glide.with(binding.imageViewBanner.context)
                .load(topHeadlineArticle.urlToImage)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(topHeadlineArticle.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(topHeadlineArticleList[position])

    override fun getItemCount(): Int = topHeadlineArticleList.size

    fun addData(list: List<TopHeadlineArticle>) {
        topHeadlineArticleList.clear()
        topHeadlineArticleList.addAll(list)
    }
}