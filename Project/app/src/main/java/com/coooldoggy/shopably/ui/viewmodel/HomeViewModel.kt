package com.coooldoggy.shopably.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.data.repository.HomeRepository
import com.coooldoggy.shopably.ui.HomeListAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel(){
    private val TAG = HomeViewModel::class.java.simpleName
    private val _shopItemList = MutableLiveData<ShopApiResponse>()
    val shopItemList: LiveData<ShopApiResponse>
        get() = _shopItemList
    val isLoadMore = MutableLiveData<Boolean>(false)

    init {
        getShopItems()
    }

    private fun getShopItems(){
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.getShopItems().let {
                    if(it.isSuccessful){
                        _shopItemList.postValue(it.body())
                    }else{
                        Log.d(TAG, "${it.errorBody()}")
                    }
                }
            }
        }
    }
}