package com.coooldoggy.shopably.data.repository

import com.coooldoggy.shopably.data.FavoriteDao
import com.coooldoggy.shopably.data.FavoriteEntity
import com.coooldoggy.shopably.data.api.ShopApiHelper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(
    private val shopApiHelper: ShopApiHelper,
    private val favoriteRepo: FavoriteDao
)  {
    suspend fun getShopItems() = shopApiHelper.getShopItems()
    suspend fun loadMoreShopItems(lastId: Int) = shopApiHelper.loadMoreShopItems(lastId)

    suspend fun getAllFavorite() = favoriteRepo.getAllFavorite()
    suspend fun insertFavorite(item: FavoriteEntity) = favoriteRepo.insertFavorite(item)
    suspend fun deleteFavorite(id: Int) = favoriteRepo.deleteFavorite(id)
    suspend fun isRowExist(id: Int) = favoriteRepo.isRowIsExist(id)
}