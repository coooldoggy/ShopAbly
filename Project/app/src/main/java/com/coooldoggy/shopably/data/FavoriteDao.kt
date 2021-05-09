package com.coooldoggy.shopably.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteEntity")
    suspend fun getAllFavorite(): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE id = :id)")
    suspend fun isRowIsExist(id : Int) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE id=:id")
    suspend fun deleteFavorite(id: Int)
}