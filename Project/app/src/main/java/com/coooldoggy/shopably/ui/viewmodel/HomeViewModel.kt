package com.coooldoggy.shopably.ui.viewmodel

import androidx.lifecycle.*
import com.coooldoggy.shopably.data.repository.HomeRepository
import com.coooldoggy.shopably.data.ShopApiResponse
import com.coooldoggy.shopably.ui.HomeListAdapter
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle, private val homeRepository: HomeRepository
): ViewModel(){

    val adapter: HomeListAdapter = HomeListAdapter()
    private val _shopItemList = MutableLiveData<ShopApiResponse>()
    val shopItemList: LiveData<ShopApiResponse>
        get() = _shopItemList

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

                    }
                }
            }
        }
    }
}