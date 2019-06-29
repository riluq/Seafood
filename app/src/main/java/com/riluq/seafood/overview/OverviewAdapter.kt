package com.riluq.seafood.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riluq.seafood.databinding.SeafoodListItemBinding
import com.riluq.seafood.network.Seafood

class OverviewAdapter(private val onClickListener: OnClickListener) : ListAdapter<Seafood, OverviewAdapter.SeafoodViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeafoodViewHolder {
        return SeafoodViewHolder(SeafoodListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SeafoodViewHolder, position: Int) {
        val seafood = getItem(position)
        holder.bind(seafood)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(seafood)
        }
    }

    class SeafoodViewHolder(private var binding: SeafoodListItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(seafood: Seafood) {
            binding.seafood = seafood
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Seafood>() {
        override fun areItemsTheSame(oldItem: Seafood, newItem: Seafood): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Seafood, newItem: Seafood): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class OnClickListener(val clickListener: (seafood: Seafood) -> Unit) {
        fun onClick(seafood: Seafood) = clickListener(seafood)
    }
}