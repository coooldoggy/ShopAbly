package com.coooldoggy.shopably.data.repository

import com.coooldoggy.shopably.data.api.ShopApiHelper
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val shopApiHelper: ShopApiHelper
) {

    suspend fun getShopItems() = shopApiHelper.getShopItems()
    suspend fun loadMoreItems(lastId: Int) = shopApiHelper.loadMoreShopItems(lastId)

}