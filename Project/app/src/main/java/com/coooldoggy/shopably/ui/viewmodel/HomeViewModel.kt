package com.coooldoggy.shopably.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.data.repository.HomeRepository
import com.coooldoggy.shopably.ui.view.BannerListAdapter
import com.coooldoggy.shopably.ui.view.GoodsListAdapter
import com.coooldoggy.shopably.ui.common.ViewModelEvent
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
    val bannerAdapter = BannerListAdapter()
    val shopAdapter = GoodsListAdapter()
    private val _eventId = MutableLiveData<ViewModelEvent<Int>>()
    val eventId : LiveData<ViewModelEvent<Int>>
        get() = _eventId

    init {
        getShopItems()
    }

    companion object{
        const val EVENT_REFRESH_DONE = 1001
        const val EVENT_NOMORE_ITEM = 1002
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

    fun loadMoreItems(){
        val lastId = shopItemList.value?.goods?.last()?.id ?: 1
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.loadMoreShopItems(lastId).let {
                    if(it.isSuccessful){
                        if (it.body()?.goods?.isEmpty() == true){
                            _eventId.postValue(ViewModelEvent(EVENT_NOMORE_ITEM))
                            return@launch
                        }
                        isLoadMore.postValue(true)
                        _shopItemList.postValue(it.body())
                    }else{
                        Log.d(TAG, "${it.errorBody()}")
                    }
                }
            }
        }
    }

    fun refresh(){
//        getShopItems()
//        _eventId.postValue(ViewModelEvent(EVENT_REFRESH_DONE))
    }
}