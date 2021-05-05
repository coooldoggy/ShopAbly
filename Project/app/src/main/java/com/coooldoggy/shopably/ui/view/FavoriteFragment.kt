package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.FragmentFavoriteBinding

class FavoriteFragment: Fragment() {
    private val TAG = FavoriteFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentFavoriteBinding
//    private val viewModel by viewModels<BookSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate<FragmentFavoriteBinding>(inflater,
            R.layout.fragment_favorite, container, false).apply {
//            model = viewModel
            lifecycleOwner = this@FavoriteFragment
        }
        return dataBinding.root
    }
}