package com.agamatech.worderworld.domain.data

import com.agamatech.worderworld.domain.manager.StoreItem

data class Ball(
    val id: String,
    val enable: Boolean,
    val selected: Boolean,
    val priceString: String,
    val skuItem: StoreItem?
)
