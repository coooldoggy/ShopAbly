package com.coooldoggy.shopably.ui

import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.databinding.ItemImageBannerBinding

class BannerViewHolder(private val binding: ItemImageBannerBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(banner: HomeListItem.BannerItem) {
        binding.model = banner
    }
}