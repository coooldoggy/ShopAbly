package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.FragmentHomeBinding
import com.coooldoggy.shopably.ui.HomeListAdapter
import com.coooldoggy.shopably.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

class HomeFragment: Fragment() {
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentHomeBinding
    val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResources()
    }

    private fun setResources(){
        dataBinding.rvHome.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = HomeListAdapter()
        }
        viewModel.shopItemList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.rvHome.adapter
            (adapter as HomeListAdapter).setData(it, viewModel.isLoadMore.value ?: false)
        })
    }
}