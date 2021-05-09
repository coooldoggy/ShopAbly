package com.coooldoggy.shopably.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.ShopApiResponse

class HomeListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = HomeListAdapter::class.java.simpleName

    companion object{
        const val VIEW_TYPE_BANNER = 1
        const val VIEW_TYPE_ITEM = 2
    }

    interface ItemClick {
        fun onClick(view: View, data: HomeListItem.ShopItem, position: Int)
    }

    var itemList = ArrayList<HomeListItem>()
    var itemClick : ItemClick? = null
    var context : Context? = null

    fun setData(resultList: ShopApiResponse, isLoadMore: Boolean) {
        val currentItemCount = itemList.size
        val bannerItem = HomeListItem.BannerItem()
        bannerItem.bannerItem = resultList.banners
        itemList.add(bannerItem)
        resultList.goods.forEach {
            itemList.add(HomeListItem.ShopItem().apply {
                shopItem = it
            })
        }

        if (isLoadMore){
            notifyItemRangeInserted(currentItemCount, itemList.size)
        }else{
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int =
        when(itemList[position]){
            is HomeListItem.BannerItem ->{
                VIEW_TYPE_BANNER
            }
            is HomeListItem.ShopItem -> {
                VIEW_TYPE_ITEM
            }
            else -> {
                VIEW_TYPE_ITEM
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this@HomeListAdapter.context = parent.context
        val adapterDelegate = DelegateMap.delegateMap[DelegateMap.delegateViewTypeMap[viewType]]
        val holder = adapterDelegate?.let{
            it.onCreateViewHolder(parent.context, parent, viewType)
        }

        return if (holder == null){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_empty, parent, false)
            Holder(view)
        }else{
            holder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val item = itemList[position]
        val aHolder = holder as? Holder
        aHolder?.let {
            it.itemClick = itemClick
        }
        val adapterDelegate = DelegateMap.delegateMap[DelegateMap.delegateViewTypeMap[viewType]]
        adapterDelegate?.let {
            context?.let { itContext ->
                adapterDelegate.onBindViewHolder(itContext, holder, item, position)
            }
        }
    }

    open class Holder(view : View) : RecyclerView.ViewHolder(view) {
        var itemClick : ItemClick? = null
    }

}