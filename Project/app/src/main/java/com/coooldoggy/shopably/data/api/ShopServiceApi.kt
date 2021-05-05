package com.coooldoggy.shopably.data.api

import com.coooldoggy.shopably.SUB_API_URL
import com.coooldoggy.shopably.data.ShopApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopServiceApi {
    @GET(SUB_API_URL)
    suspend fun getShopItems(): Response<ShopApiResponse>

    @GET(SUB_API_URL)
    suspend fun loadMoreShopItems(@Query("lastId") lastId: Int): Response<ShopApiResponse>
}