package com.coooldoggy.shopably.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.Banner
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.databinding.ItemImageBannerSlideItemBinding
import com.coooldoggy.shopably.ui.common.InfiniteLoopViewPager2Helper

class HomeListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = HomeListAdapter::class.java.simpleName

    companion object{
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_BANNER = 1
        const val VIEW_TYPE_ITEM = 2
    }

    var itemList = ArrayList<HomeListItem>()

    fun setData(resultList: ShopApiResponse, isLoadMore: Boolean) {
        val currentItemCount = itemList.size
        val bannerItem = HomeListItem.BannerItem()
        bannerItem.bannerItem = resultList.banners
        val shopItem = HomeListItem.ShopItem()
        shopItem.shopItem = resultList.goods

        itemList.add(bannerItem)
        itemList.add(shopItem)
        if (isLoadMore){
            notifyItemRangeInserted(currentItemCount, itemList.size)
        }else{
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(itemList[position]){
            is HomeListItem.BannerItem ->{
                VIEW_TYPE_BANNER
            }
            is HomeListItem.ShopItem -> {
                VIEW_TYPE_ITEM
            }
            else -> {
                VIEW_TYPE_EMPTY
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_BANNER -> {
                val view = layoutInflater.inflate(R.layout.item_image_banner, parent, false)
                return BannerViewHolder(view)
            }
            VIEW_TYPE_ITEM -> {
                val view = layoutInflater.inflate(R.layout.item_image_banner, parent, false)
                return BannerViewHolder(view)
            }
            VIEW_TYPE_EMPTY -> {
                val view = layoutInflater.inflate(R.layout.item_image_banner, parent, false)
                return BannerViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_image_banner, parent, false)
                return BannerViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            VIEW_TYPE_BANNER -> {
                val bannerHolder = holder as BannerViewHolder
                val bannerList: HomeListItem.BannerItem = itemList[position] as HomeListItem.BannerItem
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
                    Log.d(TAG, "ifNeedInit $ifNeedInit")
                    if (ifNeedInit){
                        vp2.adapter = TopImageBannerViewPagerAdapter(items)
                        holder.realPositionViewPager2 = firstPosition
                        (vp2.adapter as TopImageBannerViewPagerAdapter).notifyDataSetChanged()
                    }

                    holder.infiniteLoopViewPager2Helper?.onPageSelectedListener = object: InfiniteLoopViewPager2Helper.OnPageSelectedListener{
                        override fun onPageSelectedListener(position: Int, realPosition: Int) {
                            Log.d(TAG, "onPageSelectedListener : $position / $realPosition")
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
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        (holder as BannerViewHolder).infiniteLoopViewPager2Helper?.startAutoScroll()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        (holder as BannerViewHolder).infiniteLoopViewPager2Helper?.stopAutoScroll()
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as BannerViewHolder).infiniteLoopViewPager2Helper?.let{
            it.onPageSelectedListener = null
            it.clear()
        }
    }

    private inner class TopImageBannerViewPagerAdapter(val adapterData: List<Banner>): RecyclerView.Adapter<TopImageBannerViewHolder>(){
        private val internalData = ArrayList<Banner>()
        private lateinit var imageBannerSlideItemBinding: ItemImageBannerSlideItemBinding

        init{
            internalData.addAll(adapterData)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopImageBannerViewHolder {
            imageBannerSlideItemBinding = ItemImageBannerSlideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopImageBannerViewHolder(imageBannerSlideItemBinding)
        }

        override fun onBindViewHolder(holder: TopImageBannerViewHolder, position: Int) {
            val item = adapterData[position]
            holder.bind(item)
            imageBannerSlideItemBinding.ivBanner.apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                adjustViewBounds = true
            }
        }

        override fun getItemCount(): Int {
            return adapterData.size
        }

        fun updateDataIfNeeds(newItems: List<Banner>){
            val result = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
                override fun getOldListSize(): Int = internalData.size

                override fun getNewListSize(): Int = newItems.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return (internalData[oldItemPosition] == newItems[newItemPosition]).also {
                        Log.d(TAG, "areItemsTheSame $oldItemPosition / $newItemPosition / $it")
                    }
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return (internalData[oldItemPosition] == newItems[newItemPosition]).also {
                        Log.d(TAG, "areContentsTheSame $oldItemPosition / $newItemPosition / $it")
                    }
                }
            })
            result.dispatchUpdatesTo(this@HomeListAdapter)
        }
    }

    private inner class TopImageBannerViewHolder(private val binding: ItemImageBannerSlideItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(banner: Banner){
            binding.model = banner
        }
    }
}