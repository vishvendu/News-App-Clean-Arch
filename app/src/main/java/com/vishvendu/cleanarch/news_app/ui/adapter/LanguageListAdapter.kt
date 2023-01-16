package com.vishvendu.cleanarch.news_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishvendu.cleanarch.news_app.data.model.countrylist.CountryListItem
import com.vishvendu.cleanarch.news_app.data.model.languagelist.LanguageItem
import com.vishvendu.cleanarch.news_app.databinding.LanguageListItemsBinding
import com.vishvendu.cleanarch.news_app.utils.ItemClickListener

class LanguageListAdapter(private val context: Context, private var languageList: ArrayList<LanguageItem>) :
    RecyclerView.Adapter<LanguageListAdapter.ViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<HashMap<Int, String>>

    private val languageSelectedMap = HashMap<Int,String>()
    private var isEnabled: Boolean = false
    class ViewHolder(
        private val binding: LanguageListItemsBinding,
        private var isEnabled: Boolean,
        private var languageSelectedMap: HashMap<Int, String>,
        private var itemClickListener: ItemClickListener<HashMap<Int, String>>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: LanguageItem, position: Int) {
            binding.languageItem.text = source.label
            itemView.setOnLongClickListener{
                selectItem(binding,source,position,languageSelectedMap)
                true
            }

           /* itemView.setOnClickListener {

            }*/

            itemView.setOnClickListener {
                if(languageSelectedMap.contains(position)){
                    languageSelectedMap.remove(position)
                    binding.itemSelected.visibility = View.GONE
                    source.isSelected = false
                }else if(isEnabled){
                    selectItem(binding,source,position,languageSelectedMap)
                }


              //  context.startActivity(NewsInLanguageActivity.getStartIntent(context, source.code))
            }
        }



        private fun selectItem(binding: LanguageListItemsBinding,
            source: LanguageItem,
            position: Int, hashMap: HashMap<Int,String>
        ) {
            isEnabled = true
            hashMap.put(position,source.code)
            println("languageSelectedMap after long press $languageSelectedMap")
            source.isSelected = true
            binding.itemSelected.visibility = View.VISIBLE
            itemClickListener(languageSelectedMap)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LanguageListItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            isEnabled, languageSelectedMap,itemClickListener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(languageList[position], position)
    }


    override fun getItemCount(): Int = languageList.size

    fun addData(list: List<LanguageItem>) {
           languageList.clear()
           languageList.addAll(list)
    }
}

