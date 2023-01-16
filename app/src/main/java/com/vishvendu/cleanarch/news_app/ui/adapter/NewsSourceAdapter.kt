package com.vishvendu.cleanarch.news_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishvendu.cleanarch.news_app.data.model.newssources.Source
import com.vishvendu.cleanarch.news_app.databinding.NewsSourcesItemLayoutBinding
import com.vishvendu.cleanarch.news_app.ui.activity.NewsSourceDetailsActivity

class NewsSourceAdapter(private val context: Context, private val source: ArrayList<Source>) :
    RecyclerView.Adapter<NewsSourceAdapter.ViewHolder>() {

    class ViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source, context: Context) {
            binding.topHeadlines.text = source.name
            itemView.setOnClickListener {
                context.startActivity(NewsSourceDetailsActivity.getStartIntent(context, source.id))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            NewsSourcesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(source[position], context)

    override fun getItemCount(): Int = source.size

    fun addData(list: List<Source>) {
        source.clear()
        source.addAll(list)
    }
}