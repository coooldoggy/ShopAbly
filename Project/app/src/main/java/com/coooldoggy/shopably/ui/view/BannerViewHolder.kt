package com.coooldoggy.shopably.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.coooldoggy.shopably.databinding.ItemImageBannerBinding
import com.coooldoggy.shopably.ui.common.InfiniteLoopViewPager2Helper

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var infiniteLoopViewPager2Helper: InfiniteLoopViewPager2Helper? = null
    var realPositionViewPager2 = 0
    var positionViewPager2Indicator = 0
    var binding: ItemImageBannerBinding = ItemImageBannerBinding.bind(view)
    var viewPager: ViewPager2? = binding.vp
    var indicator = binding.tvIndicator

    init {
        infiniteLoopViewPager2Helper = InfiniteLoopViewPager2Helper(binding.vp)
    }
}