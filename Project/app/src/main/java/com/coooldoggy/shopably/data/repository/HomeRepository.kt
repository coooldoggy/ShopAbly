package com.coooldoggy.shopably.data.repository

import com.coooldoggy.shopably.data.api.ShopApiHelper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(
    private val shopApiHelper: ShopApiHelper
)  {
    suspend fun getShopItems() = shopApiHelper.getShopItems()
    suspend fun loadMoreShopItems(lastId: Int) = shopApiHelper.loadMoreShopItems(lastId)
}