package com.coooldoggy.shopably.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.ui.common.IAdapterDelegate

class GoodsDelegate: IAdapterDelegate<GoodsViewHolder, HomeListItem.ShopItem> {

    override fun onCreateViewHolder(context: Context?, parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_goods, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(context: Context?, holder: GoodsViewHolder, cardItem: HomeListItem.ShopItem, position: Int) {
        val goods = cardItem.shopItem
        goods?.let { itItem ->
            holder.favoriteBtn.apply {
                setOnClickListener {
                    val itemClick = holder.itemClick
                    itemClick?.onClick(it, cardItem, position)
                }
            }
            holder.bind(itItem)
        }
    }

    override fun onViewAttachedToWindow(holder: GoodsViewHolder) {}
    override fun onViewDetachedFromWindow(holder: GoodsViewHolder) {}
    override fun onViewRecycled(holder: GoodsViewHolder) {}

}