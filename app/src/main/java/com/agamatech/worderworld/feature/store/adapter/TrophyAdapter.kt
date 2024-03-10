package com.agamatech.worderworld.feature.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agamatech.worderworld.databinding.ItemTrophyBinding
import com.agamatech.worderworld.domain.data.Trophy

class TrophyAdapter() : ListAdapter<Trophy, RecyclerView.ViewHolder>(BallDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = BallViewHolder(
        ItemTrophyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trophy = getItem(position)
        (holder as BallViewHolder).bind(trophy)
    }

    class BallViewHolder(private val binding: ItemTrophyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trophy: Trophy) {
            binding.apply {

            }
        }
    }
}


class BallDiffCallback : DiffUtil.ItemCallback<Trophy>() {

    override fun areItemsTheSame(oldItem: Trophy, newItem: Trophy): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Trophy, newItem: Trophy): Boolean =
        oldItem.id == newItem.id && oldItem.text == newItem.text
}

