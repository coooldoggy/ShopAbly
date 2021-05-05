package com.coooldoggy.shopably.data

import com.google.gson.annotations.SerializedName

data class ShopApiResponse(
    @SerializedName("banners")
    val banners: ArrayList<Banner>,

    @SerializedName("goods")
    val goods: ArrayList<Goods>
)

data class Banner(
    @SerializedName("id")
    val id: Int,

    @SerializedName("image")
    val image: String
)

data class Goods(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("actual_price")
    val actualPrice: Int,

    @SerializedName("price")
    val price: Int,

    @SerializedName("is_new")
    val isNew: Boolean,

    @SerializedName("sell_count")
    val sellCount: Int
)