package com.agamatech.worderworld.feature.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.ItemTrophyBinding
import com.agamatech.worderworld.domain.data.Trophy

class TrophyAdapter() : ListAdapter<Trophy, RecyclerView.ViewHolder>(BallDiffCallback()) {

    private var achievedTrophies = listOf<Trophy>()

    fun setAchievedTrophies(values: List<Trophy?>) {
        achievedTrophies = values.filterNotNull()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = BallViewHolder(
        ItemTrophyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trophy = getItem(position)
        (holder as BallViewHolder).bind(trophy, achievedTrophies)
    }

    class BallViewHolder(private val binding: ItemTrophyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trophy: Trophy, achievedTrophies: List<Trophy>) {
            binding.apply {
                title.text = trophy.title
                subtitle.text = trophy.text
                val textColor = if (achievedTrophies.any { it.id == trophy.id }) trophyCard.context.getColor(R.color.lime) else trophyCard.context.getColor(R.color.orange)
                if (achievedTrophies.any { it.id == trophy.id }) {
                    title.setTextColor(textColor)
                    subtitle.setTextColor(textColor)
                    trophyCard.strokeColor = textColor
                } else {
                    title.setTextColor(textColor)
                    subtitle.setTextColor(textColor)
                    trophyCard.strokeColor = textColor
                }
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

