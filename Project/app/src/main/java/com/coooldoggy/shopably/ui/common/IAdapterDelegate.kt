package com.coooldoggy.shopably.ui.common

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface IAdapterDelegate<T : RecyclerView.ViewHolder?, T1> {
    fun onCreateViewHolder(context: Context?, parent: ViewGroup, viewType: Int): T
    fun onBindViewHolder(context: Context?, holder: T, cardItem: T1, position: Int)
    fun onViewAttachedToWindow(holder: T)
    fun onViewDetachedFromWindow(holder: T)
    fun onViewRecycled(holder: T)
}