package com.coooldoggy.shopably.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.databinding.ItemGoodsBinding

class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding: ItemGoodsBinding = ItemGoodsBinding.bind(view)
    var favoriteBtn = binding.ivFavorite

    fun bind(goods: Goods,isFavorite: Boolean){
        binding.model = goods
        binding.isFavorite = isFavorite
    }
}