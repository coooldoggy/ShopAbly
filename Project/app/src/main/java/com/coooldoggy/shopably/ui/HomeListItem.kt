package com.coooldoggy.shopably.ui

import com.coooldoggy.shopably.data.Banner
import com.coooldoggy.shopably.data.Goods
import java.io.Serializable

sealed class HomeListItem: Serializable {

    class BannerItem: HomeListItem(){
        var bannerItem: ArrayList<Banner>? = null
    }

    class ShopItem: HomeListItem(){
        var shopItem: ArrayList<Goods>? = null
    }

    class EmptyItem : HomeListItem() {}
}