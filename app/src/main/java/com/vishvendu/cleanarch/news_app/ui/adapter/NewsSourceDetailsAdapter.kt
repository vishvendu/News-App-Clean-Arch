package com.vishvendu.cleanarch.news_app.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishvendu.cleanarch.news_app.data.model.newssourcedetails.Article
import com.vishvendu.cleanarch.news_app.databinding.NewsSourceDetailsItemLayoutBinding
import javax.inject.Inject

class NewsSourceDetailsAdapter @Inject constructor(private val article: ArrayList<Article>) :
    RecyclerView.Adapter<NewsSourceDetailsAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceDetailsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Article) {
            binding.textViewTitle.text = source.title
            binding.textViewDescription.text = source.description
            binding.textViewSource.text = source.newsDetailSource.name
            Glide.with(binding.imageViewBanner.context)
                .load(source.urlToImage)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(source.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            NewsSourceDetailsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)  =
        holder.bind(article[position])

    override fun getItemCount(): Int = article.size

    fun addData(list: List<Article>) {
        article.clear()
        article.addAll(list)
    }
}