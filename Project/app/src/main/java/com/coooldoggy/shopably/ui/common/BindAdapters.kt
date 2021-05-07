package com.coooldoggy.shopably.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat

object BindAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
    }

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: View, isSelected: Boolean?) {
        isSelected?.let {
            view.isSelected = it
        }
    }

    @BindingAdapter(value = ["originalPrice", "salePrice"])
    @JvmStatic
    fun setSalePercent(view: TextView, originalPrice: Int, salePrice: Int){
        if (originalPrice == salePrice){
            view.visibility = View.GONE
        }else{
            val percent = 1 - (salePrice/originalPrice)
            view.text = "$percent%"
        }
    }

    @BindingAdapter("price")
    @JvmStatic
    fun setPriceText(view: TextView, price: Int){
        view.text = NumberFormat.getInstance().format(price)
    }
}