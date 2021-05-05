package com.coooldoggy.shopably.data.api

import com.coooldoggy.shopably.data.ShopApiResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopServiceHelperImpl @Inject constructor(private val shopServiceApi: ShopServiceApi):
    ShopApiHelper {

    override suspend fun getShopItems(): Response<ShopApiResponse> = shopServiceApi.getShopItems()
    override suspend fun loadMoreShopItems(lastId: Int): Response<ShopApiResponse> = shopServiceApi.loadMoreShopItems(lastId)

}