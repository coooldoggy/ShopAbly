package com.coooldoggy.shopably.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.Goods

class FavoriteAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var favoriteList = ArrayList<Goods>()
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onClick(data: Goods)
    }

    fun setStatusChange(goods: Goods){
        var deletedIndex = -1
        favoriteList.forEachIndexed { index, favItem ->
            if (favItem == goods){
                deletedIndex = index
                return@forEachIndexed
            }
        }
        notifyItemChanged(deletedIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_goods, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods = favoriteList[position]
        val favHolder = holder as FavoriteViewHolder
        favHolder.favoriteBtn.apply {
            setOnClickListener {
                holder.favoriteBtn.isSelected = !holder.favoriteBtn.isSelected
                itemClick?.onClick(goods)
            }
        }

        favHolder.goodsImg.clipToOutline = true
        favHolder.bind(goods, true)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}