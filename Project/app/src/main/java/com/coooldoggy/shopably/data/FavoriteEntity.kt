package com.coooldoggy.shopably.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "actualPrice") val actualPrice: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "isNew") val isNew: Boolean,
    @ColumnInfo(name = "sellCount") val sellCount: Int
)