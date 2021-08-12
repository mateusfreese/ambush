package com.ambush.ambushchallenge.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ambush.ambushchallenge.R
import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse
import com.ambush.ambushchallenge.databinding.ItemRepositoryBinding
import com.ambush.ambushchallenge.extensions.toFormattedDateTime
import java.lang.Exception

class RepositoryAdapter :
    ListAdapter<AmbushReposResponse, RepositoryAdapter.RepositoryViewHolder>(diffItemCallback) {

    companion object {
        val diffItemCallback = object : DiffUtil.ItemCallback<AmbushReposResponse>() {
            override fun areItemsTheSame(
                oldItem: AmbushReposResponse,
                newItem: AmbushReposResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AmbushReposResponse,
                newItem: AmbushReposResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class RepositoryViewHolder(
        private val itemBind: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(itemBind.root) {
        private val context: Context = itemBind.root.context

        fun bind(item: AmbushReposResponse) {
            itemBind.run {
                tvProjectName.text = getFormattedName(item.name)
                tvCreatedAtDate.text = getFormattedCreatedDate(item.createdAt.toFormattedDateTime())
                tvUpdatedAtDate.text = getFormattedUpdatedDate(item.updatedAt.toFormattedDateTime())
                tvNumberOfIssues.text = getFormattedNumberOfIssues(item.openIssues)
            }
        }

        private fun getFormattedName(name: String?): String {
            return context.getString(R.string.repository_filter_project_name, name)
        }

        private fun getFormattedCreatedDate(date: String?): String {
            return context.getString(R.string.repository_filter_created_at_date, date)
        }

        private fun getFormattedUpdatedDate(date: String?): String {
            return context.getString(R.string.repository_filter_updated_at_date, date)
        }

        private fun getFormattedNumberOfIssues(issues: Int?): String {
            return try {
                context.getString(R.string.repository_filter_issues, issues)
            } catch (e: Exception) {
                ""
            }
        }

        companion object {
            fun create(
                parent: ViewGroup
            ): RepositoryViewHolder {
                val itemBind = ItemRepositoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return RepositoryViewHolder(itemBind)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
