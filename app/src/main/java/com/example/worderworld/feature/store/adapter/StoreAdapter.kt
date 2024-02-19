package com.agamatech.worderworld.feature.store.adapter

import android.os.Handler
import android.os.Looper
import com.agamatech.worderworld.databinding.ItemBallStoreBinding
import com.agamatech.worderworld.domain.data.Ball
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agamatech.worderworld.R
import com.agamatech.worderworld.domain.manager.BallStoreItem
import com.agamatech.worderworld.utils.getDrawableId


class StoreAdapter(private val onBallClickListener: OnBallClickListener) : ListAdapter<Ball, RecyclerView.ViewHolder>(BallDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = BallViewHolder(
        ItemBallStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ball = getItem(position)
        (holder as BallViewHolder).bind(ball, onBallClickListener)
    }

    class BallViewHolder(private val binding: ItemBallStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ball: Ball, onBallClickListener: OnBallClickListener) {
            binding.apply {
                select.setOnClickListener {
                    onBallClickListener.onBallSelectClick(ball.id)
                }
                val imageName = ball.id
                val resId = getDrawableId(root.context, imageName)
                if (resId != null && resId != 0) {
                    ballImage.setImageResource(resId)
                } else {
                    ballImage.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.ball_1))
                }
                price.isGone = true
                lock.isGone = true
                if (ball.selected) {
                    active.isVisible = true
                    select.isGone = true
                    buyButton.isGone = true
                } else {
                    active.isGone = true
                    if (ball.enable) {
                        select.isVisible = true
                        buyButton.isGone = true
                    } else {
                        select.isGone = true
                        buyButton.isVisible = true
                        price.isVisible = true
                        lock.isVisible = true
                        price.text = ball.priceString
                        buyButton.setOnClickListener {
                            buyButton.isEnabled = false
                            Handler(Looper.getMainLooper()).postDelayed( {
                                buyButton.isEnabled = true
                            }, 5000)
                            onBallClickListener.onBallBuyClick(ball.skuItem)
                        }
                    }
                }
            }
        }
    }
}

interface OnBallClickListener {
    fun onBallBuyClick(item: BallStoreItem?)
    fun onBallSelectClick(id: String)
}

class BallDiffCallback : DiffUtil.ItemCallback<Ball>() {

    override fun areItemsTheSame(oldItem: Ball, newItem: Ball): Boolean =
        oldItem.id == newItem.id && oldItem.enable == newItem.enable && oldItem.selected == newItem.selected && oldItem.skuItem == newItem.skuItem

    override fun areContentsTheSame(oldItem: Ball, newItem: Ball): Boolean =
        oldItem.id == newItem.id && oldItem.enable == newItem.enable && oldItem.selected == newItem.selected && oldItem.skuItem == newItem.skuItem
}

