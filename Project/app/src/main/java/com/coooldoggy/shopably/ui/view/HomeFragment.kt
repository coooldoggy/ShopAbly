package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var dataBinding: FragmentHomeBinding
//    private val viewModel by viewModels<BookSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false).apply {
//            model = viewModel
            lifecycleOwner = this@HomeFragment
        }
        return dataBinding.root
    }
}