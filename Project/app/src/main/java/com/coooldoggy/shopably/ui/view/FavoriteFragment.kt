package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.FragmentFavoriteBinding
import com.coooldoggy.shopably.ui.viewmodel.FavoriteViewModel

class FavoriteFragment: Fragment() {
    private val TAG = FavoriteFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentFavoriteBinding
    val favViewModel by activityViewModels<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return dataBinding.root
    }
}