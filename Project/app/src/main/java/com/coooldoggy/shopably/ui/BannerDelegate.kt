package com.coooldoggy.shopably.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.ui.common.IAdapterDelegate
import com.coooldoggy.shopably.ui.common.InfiniteLoopViewPager2Helper

class BannerDelegate: IAdapterDelegate<BannerViewHolder, HomeListItem.BannerItem> {
    private val TAG = BannerDelegate::class.java.simpleName

    override fun onCreateViewHolder(context: Context?, parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_image_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(context: Context?, holder: BannerViewHolder, cardItem: HomeListItem.BannerItem, position: Int) {
        val bannerList: HomeListItem.BannerItem = cardItem
        holder.viewPager?.let{ vp2 ->
            val (firstPosition, items) = holder.
            infiniteLoopViewPager2Helper?.generateLoopItem(bannerList.bannerItem ?: emptyList())
                ?: 0 to emptyList()

            val ifNeedInit = if (vp2.adapter != null){
                if(vp2.adapter is TopImageBannerViewPagerAdapter){
                    (vp2.adapter as TopImageBannerViewPagerAdapter).updateDataIfNeeds(items)
                    false
                }else{
                    true
                }
            }else{
                true
            }
            Log.d(TAG, "ifNeedInit $ifNeedInit")
            if (ifNeedInit){
                vp2.adapter = TopImageBannerViewPagerAdapter(items)
                holder.realPositionViewPager2 = firstPosition
                (vp2.adapter as TopImageBannerViewPagerAdapter).notifyDataSetChanged()
            }

            holder.infiniteLoopViewPager2Helper?.onPageSelectedListener = object: InfiniteLoopViewPager2Helper.OnPageSelectedListener{
                override fun onPageSelectedListener(position: Int, realPosition: Int) {
                    holder.positionViewPager2Indicator = position
                    holder.realPositionViewPager2 = realPosition
                    holder.indicator.text = "${position + 1}/${bannerList.bannerItem?.size}"
                }
            }

            holder.infiniteLoopViewPager2Helper?.init()
            kotlin.runCatching {
                if (!vp2.isFakeDragging){
                    vp2.setCurrentItem(holder.realPositionViewPager2, false)
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BannerViewHolder) {
        holder.infiniteLoopViewPager2Helper?.startAutoScroll()
    }

    override fun onViewDetachedFromWindow(holder: BannerViewHolder) {
        holder.infiniteLoopViewPager2Helper?.stopAutoScroll()
    }

    override fun onViewRecycled(holder: BannerViewHolder) {
        holder.infiniteLoopViewPager2Helper?.let{
            it.onPageSelectedListener = null
            it.clear()
        }
    }
}