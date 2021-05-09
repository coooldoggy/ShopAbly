package com.coooldoggy.shopably.data.api

import com.coooldoggy.shopably.data.ShopApiResponse
import retrofit2.Response

interface ShopApiHelper {
    suspend fun getShopItems(): Response<ShopApiResponse>
    suspend fun loadMoreShopItems(lastId: Int): Response<ShopApiResponse>
}