package com.coooldoggy.shopably.ui.common

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import java.text.NumberFormat
import kotlin.math.floor

object BindAdapters {

    @BindingAdapter("frescoUrl")
    @JvmStatic
    fun loadImage(view: SimpleDraweeView, url: String) {
        val controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setUri(url).build()
        view.controller = controller
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
            val percent = 1.minus(salePrice.toDouble().div(originalPrice)).times(100)
            view.text = "${floor(percent).toInt()}%"
        }
    }

    @BindingAdapter("price")
    @JvmStatic
    fun setPriceText(view: TextView, price: Int){
        view.text = NumberFormat.getInstance().format(price)
    }

    @BindingAdapter("sellCount")
    @JvmStatic
    fun setSellCountText(view: TextView, sellCount: Int){
        view.text = "${sellCount}개 구매중"
    }
}