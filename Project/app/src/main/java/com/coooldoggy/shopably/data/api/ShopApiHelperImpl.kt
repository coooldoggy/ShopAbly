package com.coooldoggy.shopably.data.api

import com.coooldoggy.shopably.data.service.ShopApiService
import javax.inject.Inject

class ShopApiHelperImpl @Inject constructor(private val shopApiService: ShopApiService) : ShopApiHelper{
    override suspend fun getShopItems() = shopApiService.getShopItems()
    override suspend fun loadMoreShopItems(lastId: Int) = shopApiService.loadMoreShopItems(lastId)
}