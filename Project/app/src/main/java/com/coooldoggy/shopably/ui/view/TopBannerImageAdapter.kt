package com.coooldoggy.shopably.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.data.Banner
import com.coooldoggy.shopably.databinding.ItemImageBannerSlideItemBinding

class TopImageBannerViewPagerAdapter(val adapterData: List<Banner>): RecyclerView.Adapter<TopImageBannerViewHolder>(){
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
                }
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return (internalData[oldItemPosition] == newItems[newItemPosition]).also {
                }
            }
        })
        result.dispatchUpdatesTo(this@TopImageBannerViewPagerAdapter)
    }
}

class TopImageBannerViewHolder(private val binding: ItemImageBannerSlideItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(banner: Banner){
        binding.model = banner
    }
}