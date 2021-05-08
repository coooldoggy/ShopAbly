package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.databinding.FragmentHomeBinding
import com.coooldoggy.shopably.ui.BannerViewHolder
import com.coooldoggy.shopably.ui.GoodsViewHolder
import com.coooldoggy.shopably.ui.HomeListAdapter
import com.coooldoggy.shopably.ui.HomeListAdapter.Companion.VIEW_TYPE_BANNER
import com.coooldoggy.shopably.ui.HomeListAdapter.Companion.VIEW_TYPE_ITEM
import com.coooldoggy.shopably.ui.HomeListItem
import com.coooldoggy.shopably.ui.viewmodel.FavoriteViewModel
import com.coooldoggy.shopably.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentHomeBinding
    val viewModel by activityViewModels<HomeViewModel>()
    val favViewModel by activityViewModels<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResources()
    }

    private fun setResources(){
        dataBinding.rvHome.adapter = viewModel.adapter.apply {
            favClick = object: HomeListAdapter.ItemClick{
                override fun onClick(view: View, data: Goods, position: Int) {
                    lifecycleScope.launch {
                        val isExist = favViewModel.isFavorite(data).await()
                        if (isExist){
                            favViewModel.deleteFavorite(data)
                        }else{
                            favViewModel.insertFavorite(data)
                        }
                    }
                }
            }
        }

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(dataBinding.rvHome.findViewHolderForAdapterPosition(position)) {
                    is BannerViewHolder -> 1
                    is GoodsViewHolder -> 2
                    else -> 2
                }
            }
        }

        dataBinding.rvHome.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }

        viewModel.shopItemList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.rvHome.adapter
            (adapter as HomeListAdapter).setData(it, viewModel.isLoadMore.value ?: false)
        })

        favViewModel.favItemList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.rvHome.adapter
            (adapter as HomeListAdapter).favList = it
        })
    }
}