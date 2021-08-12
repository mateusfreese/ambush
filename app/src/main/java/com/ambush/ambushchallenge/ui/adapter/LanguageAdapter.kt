package com.ambush.ambushchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ambush.ambushchallenge.R
import com.ambush.ambushchallenge.data.model.Language
import com.ambush.ambushchallenge.databinding.ItemLanguageBinding

class LanguageAdapter :
    ListAdapter<Language, LanguageAdapter.LanguageViewHolder>(diffItemCallback) {

    var languageItemClickListener: ((item: Language) -> Unit)? = null

    companion object {
        private val diffItemCallback = object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }
        }
    }

    class LanguageViewHolder(
        private val itemBind: ItemLanguageBinding,
        private val itemClickListener: ((item: Language) -> Unit)?
    ) : RecyclerView.ViewHolder(itemBind.root) {

        fun bind(item: Language) {
            itemBind.run {
                tvLanguageName.text = getFormattedMainLanguageName(item.name)
                tvNumberOfProjects.text = getFormattedNumberOfRepositories(item.repositories)
            }

            itemBind.root.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }

        private fun getFormattedMainLanguageName(name: String?): String {
            itemBind.root.context.run {
                return name ?: getString(R.string.none_language)
            }
        }

        private fun getFormattedNumberOfRepositories(quantity: Int): String {
            return itemBind.root.context.getString(
                R.string.repository_list_projects_count,
                quantity
            )
        }

        companion object {
            fun create(
                parent: ViewGroup,
                itemClickListener: ((item: Language) -> Unit)?
            ): LanguageViewHolder {
                val itemBind = ItemLanguageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return LanguageViewHolder(itemBind, itemClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder.create(parent, languageItemClickListener)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
