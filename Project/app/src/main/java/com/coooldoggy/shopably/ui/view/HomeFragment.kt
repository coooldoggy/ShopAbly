package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.databinding.FragmentHomeBinding
import com.coooldoggy.shopably.ui.HomeListItem
import com.coooldoggy.shopably.ui.viewmodel.FavoriteViewModel
import com.coooldoggy.shopably.ui.viewmodel.HomeViewModel

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
        dataBinding.swRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        dataBinding.rvShop.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = viewModel.shopAdapter.apply {
                itemClick = object : GoodsListAdapter.ItemClick{
                    override fun onClick(data: Goods) {
                        favViewModel.reverseDataStatus(data)
                    }
                }
            }
        }

        dataBinding.rvBanner.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = viewModel.bannerAdapter
        }

        dataBinding.sv.viewTreeObserver.addOnScrollChangedListener {
            val view = dataBinding.sv.getChildAt(dataBinding.sv.childCount - 1)
            val diff = view.bottom - (dataBinding.sv.height + dataBinding.sv.scrollY)
            if (diff == 0){
                viewModel.loadMoreItems()
            }
        }

        viewModel.shopItemList.observe(viewLifecycleOwner, Observer { shopItem ->
            val loadMore = viewModel.isLoadMore.value ?: false
                if (loadMore){
                    viewModel.shopAdapter.setData(shopItem, loadMore)
                }else{
                    viewModel.bannerAdapter.setData(shopItem)
                    viewModel.shopAdapter.setData(shopItem, loadMore)
                }
        })

        viewModel.eventId.observe(viewLifecycleOwner, Observer {
            when(it.peekContent()){
                HomeViewModel.EVENT_REFRESH_DONE -> dataBinding.swRefresh.isRefreshing = false
            }
        })

        favViewModel.favItemList.observe(viewLifecycleOwner, Observer {
            viewModel.shopAdapter.favoriteList = it
            val deletedItem = favViewModel.deletedItem.value?.peekContent()
            if (deletedItem != null){
                viewModel.shopAdapter.setStatusChange(deletedItem)
            }
        })
    }
}