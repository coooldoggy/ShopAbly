package com.coooldoggy.shopably.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.ui.HomeListItem
import com.coooldoggy.shopably.ui.TopImageBannerViewPagerAdapter
import com.coooldoggy.shopably.ui.common.InfiniteLoopViewPager2Helper

class BannerListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    var bannerList = ArrayList<HomeListItem.BannerItem>()

    fun setData(response: ShopApiResponse){
        val bannerItem = HomeListItem.BannerItem()
        bannerItem.bannerItem = response.banners
        bannerList.add(bannerItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_image_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bannerList: HomeListItem.BannerItem = bannerList[position]
        val bannerHolder = holder as BannerViewHolder
        bannerHolder.viewPager?.let{ vp2 ->
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

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val bannerHolder = holder as BannerViewHolder
        bannerHolder.infiniteLoopViewPager2Helper?.startAutoScroll()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val bannerHolder = holder as BannerViewHolder
        bannerHolder.infiniteLoopViewPager2Helper?.stopAutoScroll()
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val bannerHolder = holder as BannerViewHolder
        bannerHolder.infiniteLoopViewPager2Helper?.let{
            it.onPageSelectedListener = null
            it.clear()
        }
    }
}