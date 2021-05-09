package com.coooldoggy.shopably.data.service

import com.coooldoggy.shopably.SUB_API_URL
import com.coooldoggy.shopably.SUB_API_URL_GOODS
import com.coooldoggy.shopably.data.ShopApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopApiService {
    @GET(SUB_API_URL)
    suspend fun getShopItems(): Response<ShopApiResponse>

    @GET(SUB_API_URL_GOODS)
    suspend fun loadMoreShopItems(@Query("lastId") lastId: Int): Response<ShopApiResponse>
}