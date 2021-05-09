package com.coooldoggy.shopably.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.ui.HomeListItem

class GoodsListAdapter:  RecyclerView.Adapter<RecyclerView.ViewHolder>()   {
    var goodsList = ArrayList<HomeListItem.ShopItem>()
    var favoriteList = ArrayList<Goods>()
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onClick(data: Goods)
    }

    fun setData(response: ShopApiResponse, isLoadMore: Boolean){
        val currentItemCount = goodsList.size
        response.goods.forEach {
            goodsList.add(HomeListItem.ShopItem().apply {
                shopItem = it
            })
        }

        if (isLoadMore){
            notifyItemRangeInserted(currentItemCount, goodsList.size)
        }else{
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_goods, parent, false)
        return GoodsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shopItem = goodsList[position]
        val goodsHolder = holder as GoodsViewHolder

        goodsHolder.goodsImg.clipToOutline = true

        shopItem.shopItem?.let{
            val goods =Goods(it.id, it.name, it.image, it.actualPrice, it.price,
                it.isNew, it.sellCount)
            var isFavorite = false
            favoriteList.forEach {favItem->
                if (favItem.id == goods.id){
                    isFavorite = true
                }
            }
            goodsHolder.bind(goods, isFavorite)
            goodsHolder.favoriteBtn.apply {
                setOnClickListener {
                    holder.favoriteBtn.isSelected = !holder.favoriteBtn.isSelected
                    itemClick?.onClick(goods)
                }
            }
        }
    }
}