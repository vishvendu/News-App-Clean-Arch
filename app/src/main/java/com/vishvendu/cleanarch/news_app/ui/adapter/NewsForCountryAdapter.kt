package com.vishvendu.cleanarch.news_app.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishvendu.cleanarch.news_app.data.model.newsforcountry.Article
import com.vishvendu.cleanarch.news_app.databinding.NewsForCountryItemsBinding

class NewsForCountryAdapter(private val article: ArrayList<Article>) :
RecyclerView.Adapter<NewsForCountryAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsForCountryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Article) {
            binding.textViewTitle.text = source.title
            binding.textViewDescription.text = source.description
            binding.textViewSource.text = source.author
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
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder =
        DataViewHolder(
            NewsForCountryItemsBinding.inflate(
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