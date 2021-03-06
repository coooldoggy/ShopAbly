package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.databinding.FragmentFavoriteBinding
import com.coooldoggy.shopably.ui.viewmodel.FavoriteViewModel

class FavoriteFragment: Fragment() {
    private val TAG = FavoriteFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentFavoriteBinding
    val viewModel by activityViewModels<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResources()
    }

    private fun setResources(){
        dataBinding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = viewModel.favAdapter.apply {
                itemClick = object : FavoriteAdapter.ItemClick{
                    override fun onClick(data: Goods) {
                        viewModel.reverseDataStatus(data)
                    }
                }
            }
        }

        viewModel.favItemList.observe(viewLifecycleOwner, Observer {
            viewModel.favAdapter.favoriteList = it
            viewModel.favAdapter.notifyDataSetChanged()
        })

    }
}