package com.coooldoggy.shopably.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.ItemImageBannerBinding

class HomeListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_BANNER = 1
        const val VIEW_TYPE_ITEM = 2
    }

    var itemList = ArrayList<HomeListItem>()

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
        when(viewType){
            VIEW_TYPE_BANNER -> {
                val dataBinding = DataBindingUtil.inflate<ItemImageBannerBinding>(layoutInflater, R.layout.item_image_banner, parent, false)
                return BannerViewHolder(dataBinding)
            }
            VIEW_TYPE_ITEM -> {}
            VIEW_TYPE_EMPTY -> {}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}