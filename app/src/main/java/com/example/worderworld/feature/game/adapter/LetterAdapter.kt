package com.example.worderworld.feature.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agamatech.worderworld.R
import com.agamatech.worderworld.databinding.WidgetLetterViewBinding
import com.example.worderworld.feature.game.LetterData
import com.example.worderworld.feature.game.LetterState

class LetterAdapter() : ListAdapter<LetterData, LetterAdapter.VH>(
    LetterAdapter
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WidgetLetterViewBinding.inflate(inflater, parent, false)

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, holder.binding)
    }

    inner class VH(val binding: WidgetLetterViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: LetterData, binding: WidgetLetterViewBinding) {
            when (item.state) {
                LetterState.INPUT -> {
                    binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.blue)
                }
                LetterState.RESULT_WRONG_PLACE -> {
                    binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.orange)
                }
                LetterState.RESULT_FALSE -> {
                    binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.grey_light)
                }
                LetterState.RESULT_OK -> {
                    binding.keyCard.strokeColor = ContextCompat.getColor(binding.keyCard.context, R.color.lime)
                }
            }
            binding.keyText.text = item.value.toUpperCase()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<LetterData>() {
        override fun areItemsTheSame(oldItem: LetterData, newItem: LetterData): Boolean =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: LetterData, newItem: LetterData): Boolean =
            oldItem == newItem
    }

}